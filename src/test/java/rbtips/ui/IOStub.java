
package rbtips.ui;


import rbtips.io.IO;

public class IOStub implements IO {
    
    private String[] commands;
    private int index;

    /**
     *
     * @param commands
     * @param index
     */
    public IOStub(String... commands) {
        this.commands = commands;
        this.index = -1;
    }

    public void setCommands(String... commands) {
        index = -1;
        this.commands = commands;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public boolean excecute() {
        return index + 1 == commands.length;
    }
    
    @Override
    public String nextCommand() {
        index++;
        return commands[index];
    }
    
}
