package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.*;
import ggc.app.exceptions.*;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
    addIntegerField("paymentDeadline", Prompt.paymentDeadline());
    addStringField("productKey", Prompt.productKey());
    addIntegerField("ammount", Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      _receiver.saleTransaction(_receiver.id(), stringField("partnerKey"),
      stringField("productKey"), integerField("ammount"), 0, 0, integerField("paymentDeadline"),
      "");
    }
    catch(InvalidPartnerKeyException e) {
      throw new UnknownPartnerKeyException(e.getKey());
    }
    catch(InvalidProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
    catch(InvalidProductException e) {
      throw new UnavailableProductException(e.getKey(), e.getRequested(), e.getAvailable());
    }
  }

}
