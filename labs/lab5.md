# Lab 5:





## Files

### Docker-compose.yaml

```yaml

version: '2'
services:
  mongo:
    image: mongo
    ports:
      - "27017:27017"
  zookeeper:
    image: confluentinc/cp-zookeeper
    ports:
      - "2181:2181"
    environment:
        ZOOKEEPER_CLIENT_PORT: 2181
  kafka:
    image: confluentinc/cp-kafka
    links:
     - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
  invoicing:
    build: invoicing
    links:
     - zookeeper
     - kafka
     - mongo
    depends_on:
     - kafka
     - mongo
    volumes:
     - ./invoicing:/src
  invoicingtester:
    build: invoicingTester
    links:
     - zookeeper
     - kafka
     - invoicing
    depends_on:
     - invoicing
    volumes:
     - ./invoicingTester:/src


```

### InvoiceCommandHandler.java

```

@Component
public class InvoiceCommandHandler {

	@Autowired
	private InvoiceController invoiceController;
	
	@StreamListener(InvoicingChannels.INVOICE_VALIDATION_REQUEST_CHANNEL)
	@SendTo(InvoicingChannels.INVOICE_VALIDATION_REPLY_CHANNEL)
	public String validateInvoice(String patientSSN) {
		System.out.println("Incomming validation request...");
		JSONObject ret = new JSONObject();
		try {
			return ret.put("patientSSN", patientSSN ).put("reply", this.invoiceController.isNewInvoiceAccpeted(patientSSN)).toString();
		}catch(JSONException e) {
			//In a real application the handling will be done properly.
			e.printStackTrace();
			return null;
		}
	}
	
	
	@StreamListener(InvoicingChannels.INVOICE_CREATION_REQUEST_CHANNEL)
	@SendTo(InvoicingChannels.INVOICE_CREATION_REPLY_CHANNEL)
	public String createInvoice(String patientSSN, String patientName, String patientAddress, String roomNumber, boolean shared) {
		System.out.println("Invoice creation request...");
		Invoice invoice = invoiceController.createPatientInvoice(patientSSN, patientName, patientAddress, roomNumber,
				shared ? RoomInfo.RoomType.SHARED : RoomInfo.RoomType.SINGLE);
		JSONObject ret = new JSONObject();
		try {
			ret.put("patientSSN", patientSSN);
			ret.put("invoiceID", invoice.getInvoiceID());
			return ret.toString();
		}catch(JSONException e) {
			//In a real application the handling will be done properly.
			return null;
		}
	}
	
	//... other methods can be added here
	
}

```

### InvoicingChannels.java (interface)

```
public interface InvoicingChannels {

	final String INVOICE_VALIDATION_REQUEST_CHANNEL = "InvoiceValidationRequest";
	final String INVOICE_VALIDATION_REPLY_CHANNEL   = "InvoiceValidationReply";
	
	final String INVOICE_CREATION_REQUEST_CHANNEL   = "InvoiceCreationRequest";
	final String INVOICE_CREATION_REPLY_CHANNEL     = "InvoiceCreationReply";

	@Input(INVOICE_VALIDATION_REQUEST_CHANNEL)
	SubscribableChannel invoiceValidationRequest();
	
	@Output(INVOICE_VALIDATION_REPLY_CHANNEL)
	MessageChannel invoiceValidationReply();
	
	@Input(INVOICE_CREATION_REQUEST_CHANNEL)
	SubscribableChannel invoiceCreationRequest();

	@Output(INVOICE_CREATION_REPLY_CHANNEL)
	MessageChannel invoiceCreationReply();
}

```

### InvoiceRepository.java

```
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{

	@Query("{paid : false, patientInfo.ssn : ?0}")
	List<Invoice> getUnpaidInvoices(String patientSSN);
	
	@Query("{patientInfo.ssn : ?0}")
	List<Invoice> getByPatientId(String patientSSN);
	
	Invoice findByInvoiceID(String invoiceId);
}

```

### Invoice.java

```

@Document(collection="Invoice")
public class Invoice {
	@Id
	private String invoiceID;
	private final RecipientInfo recipientInfo;
	private final PatientInfo patientInfo;
	private final RoomInfo roomInfo;
	private final List<InvoiceLineItem> invoiceLineItems;
	private boolean paid;
	
	// ctor & getters 

	public List<InvoiceLineItem> getInvoiceLineItems() {
		return new ArrayList<InvoiceLineItem>(invoiceLineItems);
	}
	
	public void addInvoiceLineItem(String name, double price, int quantity) throws InvoiceClosedException {
		if( ! paid) {
			this.invoiceLineItems.add(new InvoiceLineItem(name, price, quantity));
		}else {
			throw new InvoiceClosedException();
		}
	}
	
	public boolean isPaid() {
		return paid;
	}
	
	public void pay() {
		this.paid = true;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format(
				"Invoice {0}: " 
				+ "\n\t paid: {1}"
				+ "\n\t patient: {2}",
		
				this.invoiceID,
				this.paid,
				this.patientInfo
				);
	}
	
}
```