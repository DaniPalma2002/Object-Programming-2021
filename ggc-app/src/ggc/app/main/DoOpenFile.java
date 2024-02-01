package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import ggc.app.exceptions.FileOpenFailedException;
import ggc.exceptions.UnavailableFileException;
import java.io.FileNotFoundException;

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    addStringField("fileName", Prompt.openFile());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      _receiver.load(stringField("fileName"));
    }
    catch (UnavailableFileException e) {
      throw new FileOpenFailedException(e.getFilename());
    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
