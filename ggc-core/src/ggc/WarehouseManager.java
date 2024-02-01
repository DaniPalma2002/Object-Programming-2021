package ggc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.reflect.Field;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ggc.exceptions.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current store. */
  private String _filename = "";

  /** The warehouse itself. */
  private Warehouse _warehouse = new Warehouse();




  public String getFilename() { return _filename; }

  public void setFilename(String s) { _filename = s; }
  
  public int date() { return _warehouse.getDate(); }

  public int id() { return _warehouse.getId(); }

  public void advanceDate(int n) throws InvalidTimeException{
    _warehouse.advanceDate(n);
  }

  public double availableBalance() { return _warehouse.getAvailableBalance(); }
  public double accountingBalance() { return _warehouse.getAccountingBalance(); }
  
  public void registerPartner(String key, String name, String address) throws 
      InvalidPartnerKeyException{
    _warehouse.addPartner(key, name, address);
  }

  public Collection<Partner> showPartners() {
    return(_warehouse.showAllPartners());
  }

  public Partner showPartner(String key) throws InvalidPartnerKeyException{
    return _warehouse.showSpecificPartner(key);
  } 

  public Collection<Product> showProducts() {
    return(_warehouse.showAllProducts());
  }

  public Collection<Batch> showBatches() {
    return (_warehouse.showAllBatches());
  }
  
  public Collection<Batch> showBatchesByPartnerId(String partnerId) throws InvalidPartnerKeyException{
    return _warehouse.showBatchesByPartner(partnerId);
  }

  public Collection<Batch> showBatchesByProductId(String productId) throws InvalidProductKeyException{
    return _warehouse.showBatchesByProduct(productId);
  }
  
  public Transaction showTransaction(int key) throws InvalidTransactionKeyException {
    return _warehouse.showSpecificTransaction(key);
  }

  public void acquisitionTransaction(int id, String partnerId, String productId, int quantity,
    double paidValue, int paymentDay) throws InvalidPartnerKeyException, InvalidProductKeyException {
    
    _warehouse.addAcquisitionTransaction(id, partnerId, productId, quantity, paidValue, paymentDay);
  }

  public void saleTransaction(int id, String partnerId, String productId, int quantity,
    double baseValue, double valuetoPay, int limitDate, String paymentDate) throws 
    InvalidPartnerKeyException, InvalidProductKeyException, InvalidProductException {

      _warehouse.addSaleTransaction(id, partnerId, productId, quantity, baseValue, valuetoPay, limitDate, paymentDate);
  }


  public Collection<Batch> batchesUnderGivenPrice(double priceLimit) {
    return _warehouse.showBatchesUnderGivenPrice(priceLimit);
  }

  public Collection<SaleTransaction> paymentsByPartner(String partnerId) 
    throws InvalidPartnerKeyException {

      return _warehouse.showPaymentsByPartner(partnerId);
  }

  public Collection<AcquisitionTransaction> partnerAcquisitions(String partnerId) 
    throws InvalidPartnerKeyException {

    return _warehouse.showPartnerAcquisitions(partnerId);
  }

  public void createBreakdownTransaction(String partnerId, String productId, int quantity) 
    throws InvalidPartnerKeyException, InvalidProductKeyException {

    _warehouse.registerBreakdownTransaction(partnerId, productId, quantity); 
  }

  public void getPayment(int id) throws InvalidTransactionKeyException {
    _warehouse.receivePayment(id);
  }


























//----------------------------------------------------------------------------
  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException {
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename));
      out.writeObject(_warehouse);
      out.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    } 
  }





  
  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, FileNotFoundException {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)); 
      _warehouse = (Warehouse)in.readObject();
      in.close();
      _filename = filename;
    }
    catch (FileNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
    catch (IOException e) {
      e.printStackTrace();
    } 
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
	    _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException e) {
	    throw new ImportFileException(textfile);
    }
  }

  
}
