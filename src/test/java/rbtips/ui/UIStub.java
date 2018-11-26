
package rbtips.ui;

import rbtips.domain.AppService;


public class UIStub implements UI {

    private IO io;
    private AppService app;

    public UIStub(IO io, AppService app) {
        this.io = io;
        this.app = app;
    }
    
    
    
    @Override
    public void mainMenu() {
        String command = io.nextCommand();
        
        if (command.equals("n")) {
            
        }
    }

    @Override
    public void newTip() {
        String headline = io.nextCommand();
        String author = io.nextCommand();
        String url = io.nextCommand();
        app.saveArticle(headline, author, url, "tagi");
    }

    @Override
    public void showTips() {
        
    }

    @Override
    public void search() {

    }


}
