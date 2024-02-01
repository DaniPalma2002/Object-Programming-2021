package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.*;
import ggc.app.exceptions.*;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partnerId", Prompt.partnerKey());
    addStringField("productId", Prompt.productKey());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      _receiver.createBreakdownTransaction(stringField("partnerId"), stringField("productId"), 0);
    }
    catch(InvalidPartnerKeyException e) {
      throw new UnknownPartnerKeyException(e.getKey());
    }
    catch(InvalidProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
  }

}
