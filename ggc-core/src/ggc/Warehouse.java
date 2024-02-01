package ggc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.ClosedSelectorException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Map.Entry;

import ggc.exceptions.*;


/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  /** Global time */
  private int _date = 0;
  private int _id = 0;

  /** Global balance */
  private double _availableBalance = 0.0;
  private double _accountingBalance = 0.0;

  
  private Map<String, Partner> _partners = new TreeMap<>();
  private Map<String, Product> _products = new TreeMap<>();
  private Map<Batch, String> _batches = new TreeMap<>();
  private Map<Integer, Transaction> _transactions = new TreeMap<>();
  private Map<String, AcquisitionTransaction> _acqTransactions = new TreeMap<>();
  




/** Main =====================================================================*/

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, FileNotFoundException{
    try (BufferedReader reader = new BufferedReader(new FileReader(txtfile))) {
      String s;
      while ((s = reader.readLine()) != null) {
        String line = new String(s.getBytes(), "UTF-8");
        String[] fields = line.split("\\|");
        
        switch (fields[0]) {
          case "PARTNER" -> addPartner(fields[1], fields[2], fields[3]);
          case "BATCH_S" -> {
            double d = Double.parseDouble(fields[3]);
            int i = (int) Math.round(d);
            addBatch(batchProduct(fields[1], i, 
              Integer.parseInt(fields[4])), fields[2], i, Integer.parseInt(fields[4]));
            addSimpleProduct(fields[1], i, Integer.parseInt(fields[4]));
          }
          case "BATCH_M" -> {
            double d = Double.parseDouble(fields[3]);
            int i = (int) Math.round(d);
            addBatch(batchProduct(fields[1], i, 
              Integer.parseInt(fields[4])), fields[2], i, Integer.parseInt(fields[4]));
            addCompoundProduct(fields[1], i, 
              Integer.parseInt(fields[4]), Double.parseDouble(fields[5]), fields[6]);
          }
          default -> throw new BadEntryException(fields[0]);
        }
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    } 
    catch (InvalidPartnerKeyException e) {
      e.printStackTrace();
    } 
    catch (BadEntryException e) {
      e.printStackTrace();
    }
  }
//......................................//
  /**
   * app.main.DoDisplayDate
   * @return date
   */
  public int getDate() { 
    return _date; 
  }
//......................................//
  /**
   * 
   * @return transactionId
   */
  public int getId() { 
    return _id; 
  }
//......................................//
  /**
   * app.main.DoAdvanceDate
   * @param n
   * @throws InvalidTimeException
   */
  public void advanceDate(int n) throws InvalidTimeException {
    if (n <= 0)
      throw new InvalidTimeException(n);
    _date += n;
  }
//......................................//
  /**
   * 
   * @return availableBalance
   */
  public double getAvailableBalance() { 
    return _availableBalance; 
  }
//......................................//
  /**
   * 
   * @param balance
   */
  public void setAvailableBalance(double balance) { 
    _availableBalance = balance;
  }
//......................................//
  /**
   * 
   * @return accountingBalance
   */
  public double getAccountingBalance() { return _accountingBalance; }
//......................................//
  /**
   * 
   * @param balance
   */
  public void setAccountingBalance(double balance) {
    _accountingBalance = balance;
  }

/** Products =================================================================*/

  /**
   * Add simple product to TreeMap _products
   * @param idProduct
   * @param maxPrice
   * @param stock
   */
  public void addSimpleProduct(String idProduct, int maxPrice, int stock) {
    if (_products.containsKey(idProduct.toUpperCase())) {
      int oldStock = _products.get(idProduct).getStock();
      int oldPrice = _products.get(idProduct).getMaxPrice();
      stock += oldStock;
      if (maxPrice > oldPrice)
        oldPrice = maxPrice;
      Product newP = new SimpleProduct(idProduct, oldPrice, stock);
      _products.put(idProduct.toUpperCase(), newP);
    }
    else {
    Product p = new SimpleProduct(idProduct, maxPrice, stock);
    _products.put(idProduct.toUpperCase(), p);
    }
  }
//......................................//
  /**
   * Add compound product to TreeMap _products
   * @param idProduct
   * @param maxPrice
   * @param stock
   * @param tax
   * @param components
   */
  public void addCompoundProduct(String idProduct, int maxPrice, int stock, 
    double tax, String components) {
      if (_products.containsKey(idProduct.toUpperCase())) {
        int oldStock = _products.get(idProduct).getStock();
        int oldPrice = _products.get(idProduct).getMaxPrice();
        stock += oldStock;
        if (maxPrice > oldPrice)
          oldPrice = maxPrice;
        Product newP = new CompoundProduct(idProduct, oldPrice, stock, tax, components);
        _products.put(idProduct.toUpperCase(), newP);
      }
      else {
      Product p = new CompoundProduct(idProduct, maxPrice, stock, tax, components);
      _products.put(idProduct.toUpperCase(), p);
      }
  }
//......................................//
  /**
   * Put new batch to TreeMap _batches
   * @param p
   * @param partnerKey
   */
  public void addBatch(Product p, String partnerKey, double price, int stock) {
    Batch b = new Batch(p, partnerKey, price, stock);
    _batches.put(b, p.getIdProduct().toUpperCase());
  }
//......................................//
  /**
   * 
   * @param idProduct
   * @param maxPrice
   * @param stock
   * @return product for print batch in showAllBatches
   */
  public Product batchProduct(String idProduct, int maxPrice, int stock) {
    Product p = new SimpleProduct(idProduct, maxPrice, stock);
    return p;
  }
//......................................//
  /**
   * app.products.DoShowAllProducts
   * @return all products
   */
  public Collection<Product> showAllProducts() {
    return Collections.unmodifiableCollection(_products.values());
  }
//......................................//
  /**
   * app.products.DoShowAvailableBatches
   * @return all batches
   */
  public Collection<Batch> showAllBatches() {
    List<Batch> _b = new ArrayList<>();
    for (Map.Entry<Batch, String> entry : _batches.entrySet())
      if (entry.getKey().getProduct().getStock() != 0)
        _b.add(entry.getKey());
    return Collections.unmodifiableCollection(_b);
  }
//......................................//
  /**
   * app.products.DoShowBatchesByPartner
   * @param partnerId
   * @return Collection of batches that have the requested partner key
   * @throws InvalidPartnerKeyException
   */
  public Collection<Batch> showBatchesByPartner(String partnerId) throws InvalidPartnerKeyException {
    List<Batch> _b = new ArrayList<>();
    for (Map.Entry<Batch, String> entry : _batches.entrySet()) {
      if (partnerId.equalsIgnoreCase(entry.getKey().getPartnerKey()))
        _b.add(entry.getKey());
    }
    if (_b.isEmpty()) {
      if (!_partners.containsKey(partnerId.toUpperCase()))
        throw new InvalidPartnerKeyException(partnerId);
    }
    return Collections.unmodifiableCollection(_b);
  }
//......................................//
  /**
   * app.products.DoShowBatchesByProduct
   * @param productId
   * @return Collection of batches that have the requested product key
   * @throws InvalidProductKeyException
   */
  public Collection<Batch> showBatchesByProduct(String productId) throws InvalidProductKeyException {
    List<Batch> _b = new ArrayList<>();
    for (Map.Entry<Batch, String> entry : _batches.entrySet()) {
      if (productId.equalsIgnoreCase(entry.getKey().getProduct().getIdProduct()))
        _b.add(entry.getKey());
    }
    if (_b.isEmpty()) 
      throw new InvalidProductKeyException(productId);
    return Collections.unmodifiableCollection(_b);
  }

/** Partner ==================================================================*/

  /**
   * app.partner.DoShowPartner
   * @param key
   * @return specific partner
   * @throws InvalidPartnerKeyException
   */
  public Partner showSpecificPartner(String key) throws InvalidPartnerKeyException {
    if (!_partners.containsKey(key.toUpperCase()))
      throw new InvalidPartnerKeyException(key);
    Partner p = _partners.get(key.toUpperCase());
    return p;
  }
//......................................//
  /**
   * app.partners.DoShowAllPartners
   * @return all partners
   */
  public Collection<Partner> showAllPartners() {
      return Collections.unmodifiableCollection(_partners.values());
  }
//......................................//
  /**
   * app.partner.DoRegisterPartner
   * @param key
   * @param name
   * @param address
   * @throws InvalidPartnerKeyException
   */
  public void addPartner(String key, String name, String address) throws
    InvalidPartnerKeyException {

    if (_partners.containsKey(key.toUpperCase()))
      throw new InvalidPartnerKeyException(key);
    Partner p = new Partner(key, name, address);
    _partners.put(key.toUpperCase(), p);
  }
//......................................//
  /**
   * app.partners.DoShowPartnerAcquisitions
   * @param partnerId
   * @return all acquisitions from the partner with the asked partnerId
   * @throws InvalidPartnerKeyException
   */
  public Collection<AcquisitionTransaction> showPartnerAcquisitions(String partnerId) 
    throws InvalidPartnerKeyException {

    List<AcquisitionTransaction> _t = new ArrayList<>();
    if (!_partners.containsKey(partnerId.toUpperCase()))
      throw new InvalidPartnerKeyException(partnerId);
    for (Map.Entry<String, AcquisitionTransaction> entry : _acqTransactions.entrySet() ) {
      if(entry.getKey().equalsIgnoreCase(partnerId))
        _t.add(entry.getValue());
    }
    return Collections.unmodifiableCollection(_t);
  }



/** Transaction ============================================================= */ 

  /**
   * app.transactions.DoRegisterAcquisitionTransaction
   * @param id
   * @param partnerId
   * @param productId
   * @param quantity
   * @param paidValue
   * @param paymentDay
   * @throws InvalidPartnerKeyException
   * @throws InvalidProductKeyException
   */
  public void addAcquisitionTransaction(int id, String partnerId, String productId, int quantity,
    double paidValue, int paymentDay) throws InvalidPartnerKeyException, InvalidProductKeyException {
      
    if (!_partners.containsKey(partnerId.toUpperCase()))
      throw new InvalidPartnerKeyException(partnerId);
    if (!_products.containsKey(productId.toUpperCase()))
      throw new InvalidProductKeyException(productId);

    _products.get(productId.toUpperCase()).addStock(quantity);
    if(_products.get(productId.toUpperCase()).getMaxPrice() < paidValue)
      _products.get(productId.toUpperCase()).setMaxPrice((int)Math.round(paidValue));
    
    Transaction t = new AcquisitionTransaction(id, partnerId, productId, quantity, paidValue * quantity, paymentDay);
    _transactions.put(_id, t);
    AcquisitionTransaction acqT = new AcquisitionTransaction(id, partnerId, productId, quantity, paidValue * quantity, paymentDay);
    _acqTransactions.put(partnerId, acqT);
    _id += 1;
    _accountingBalance -= (paidValue * quantity);
    _availableBalance -= (paidValue * quantity);
    addBatch(batchProduct(productId.toUpperCase(), (int)Math.round(paidValue), quantity), partnerId.toUpperCase(), paidValue, quantity);
    _partners.get(partnerId.toUpperCase()).addValueOfAcquisitions(paidValue * quantity);
  }
//......................................//
  /**
   * app.transactions.DoRegisterSaleTransaction
   * @param id
   * @param partnerId
   * @param productId
   * @param quantity
   * @param baseValue
   * @param valuetoPay
   * @param limitDate
   * @param paymentDate
   * @throws InvalidPartnerKeyException
   * @throws InvalidProductKeyException
   * @throws InvalidProductException
   */
  public void addSaleTransaction(int id, String partnerId, String productId, int quantity,
    double baseValue, double valuetoPay, int limitDate, String paymentDate) throws 
    InvalidPartnerKeyException, InvalidProductKeyException, InvalidProductException {

    if (!_partners.containsKey(partnerId.toUpperCase()))
      throw new InvalidPartnerKeyException(partnerId);
    if (!_products.containsKey(productId.toUpperCase()))
      throw new InvalidProductKeyException(productId);
    if(_products.get(productId.toUpperCase()).getStock() < quantity)
      throw new InvalidProductException(productId, quantity, _products.get(productId).getStock());

    int remainingQuantity = quantity;
    int stock = 0;
    double newValue = 0;
    for (Map.Entry<Batch, String> entry : _batches.entrySet()) {
      if(remainingQuantity != 0 && productId.equalsIgnoreCase(entry.getKey().getProduct().getIdProduct())) {
        stock = entry.getKey().getProduct().getStock();
        if(stock > remainingQuantity) {
          entry.getKey().getProduct().setStock(stock - remainingQuantity);
          newValue += (remainingQuantity * entry.getKey().getProduct().getMaxPrice());
          remainingQuantity = 0;
        }
        if(stock == remainingQuantity) {
          entry.getKey().getProduct().setStock(0);
          newValue += (remainingQuantity * entry.getKey().getProduct().getMaxPrice());
          remainingQuantity = 0;
        }
        else {
          remainingQuantity -= stock;
          newValue += (stock * entry.getKey().getProduct().getMaxPrice());
        }
      }
    }
    _products.get(productId.toUpperCase()).setStock(remainingQuantity);
    Transaction t = new SaleTransaction(id, partnerId, productId, 
                    quantity, newValue, newValue, limitDate, paymentDate);
    _transactions.put(_id, t);
    _id += 1;
    _batches.entrySet().removeIf(entry -> (entry.getKey().getProduct().getStock() == 0));
  }
//......................................//
  /**
   * app.transactions.DoShowTransaction
   * @param key
   * @return Transaction with the asked transactionkey 
   * @throws InvalidTransactionKeyException
   */
  public Transaction showSpecificTransaction(int key) throws InvalidTransactionKeyException {
    if (!_transactions.containsKey(key))
      throw new InvalidTransactionKeyException(key);
    Transaction p = _transactions.get(key);
    return p;
  }
//......................................//
  /**
   * app.transactions.DoRegisterBreakdownTransaction
   * @param partnerId
   * @param productId
   * @param quantity
   * @throws InvalidPartnerKeyException
   * @throws InvalidProductKeyException
   */
  public void registerBreakdownTransaction(String partnerId, String productId, int quantity) 
    throws InvalidPartnerKeyException, InvalidProductKeyException {
    
    if (!_partners.containsKey(partnerId.toUpperCase()))
      throw new InvalidPartnerKeyException(partnerId);
    if (!_products.containsKey(productId.toUpperCase()))
      throw new InvalidProductKeyException(productId);
  
    Transaction t = new Transaction(_id, partnerId, productId, quantity);
    _id =+ 1;
  }

  void receivePayment(int id) throws InvalidTransactionKeyException {
    if (!_transactions.containsKey(id))
      throw new InvalidTransactionKeyException(id);
  }

/** Lookup ===================================================================*/
  
/**
   * app.lookups.DoLookupProductBatchesUnderGivenPrice
   * @param priceLimit
   * @return Batches under given price
   */
  public Collection<Batch> showBatchesUnderGivenPrice(double priceLimit) {
    List<Batch> _b = new ArrayList<>();
    for (Map.Entry<Batch, String> entry : _batches.entrySet()) {
      if (entry.getKey().getProduct().getMaxPrice() < priceLimit)
        _b.add(entry.getKey());
    }
    return Collections.unmodifiableCollection(_b);
  }
//......................................//
  /**
   * app.lookups.DoLookupPaymentsByPartner
   * @param partnerId
   * @return SaleTransactions already paid from partner with partnerId 
   * @throws InvalidPartnerKeyException
   */
  public Collection<SaleTransaction> showPaymentsByPartner(String partnerId) 
    throws InvalidPartnerKeyException {

    if (!_partners.containsKey(partnerId.toUpperCase()))
      throw new InvalidPartnerKeyException(partnerId);

    return _partners.get(partnerId.toUpperCase()).showCompletePayments();
  }

}



