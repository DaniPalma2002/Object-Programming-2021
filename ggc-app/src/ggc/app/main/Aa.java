package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

/**
 * Show all products.
 */
class Aa extends Command<WarehouseManager> {

  Aa(WarehouseManager receiver) {
    super("aaa", receiver);
  }

  @Override
  public final void execute() throws CommandException {
    //_display.popup(_receiver.showProducts());
  }

}
