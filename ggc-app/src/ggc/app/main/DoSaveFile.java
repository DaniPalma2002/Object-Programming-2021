package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import java.io.IOException;
import java.io.FileNotFoundException;
import ggc.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;


/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {


  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    try {
      if (_receiver.getFilename().equals("")) {
        _receiver.setFilename(Form.requestString(Prompt.newSaveAs()));
        _receiver.saveAs(_receiver.getFilename());
      }
      _receiver.saveAs(_receiver.getFilename());
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }  
  }
}
