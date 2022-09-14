package securityBean;

/*
 * SessionExpired.java
 *
 * Created on Oct 5, 2013, 3:33:20 AM
 * Copyright Administrator
 */




/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */


public class SessionExpired {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private int __placeholder;

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public SessionExpired() {
        SessionInvalidate sessionInvalidate = new SessionInvalidate();
        sessionInvalidate.sessionDestroy();
    }

   
}

