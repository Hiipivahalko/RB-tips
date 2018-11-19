
package rbtips.ui;

import java.util.Scanner;


public class CommandlineIO implements IO {
    
    private Scanner reader;

    public CommandlineIO(Scanner reader) {
        this.reader = reader;
    }
    
    @Override
    public String nextCommand() {
        return reader.nextLine();
    }
    
}
