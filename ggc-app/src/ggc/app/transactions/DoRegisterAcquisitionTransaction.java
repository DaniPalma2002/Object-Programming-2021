package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.InvalidPartnerKeyException;
import ggc.exceptions.InvalidProductKeyException;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
    addStringField("productKey", Prompt.productKey());
    addRealField("price", Prompt.price());
    addIntegerField("ammount", Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      _receiver.acquisitionTransaction(_receiver.id(), stringField("partnerKey"),
      stringField("productKey"), integerField("ammount"), realField("price"), 
      _receiver.date());
    }
    catch(InvalidPartnerKeyException e) {
      throw new UnknownPartnerKeyException(e.getKey());
    }
    catch(InvalidProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
  }

}
