package commun;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;



/**
 * 
 * Classe définissant le format des messages logs.
 * 
 * @author Gaylord Cherencey, Valentin Brémond, Florian Massacret
 *
 * @version 1.0
 *
 */
public class FormatLog extends Formatter
{
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
    
    
    
    // CONSTRUCTEURS
    
    
    
    public String format (LogRecord record)
    {
        String builder;
        
        builder = df.format (new Date (record.getMillis ())) + " - ";
        builder += "[" + record.getLevel () + "] - ";
        builder += formatMessage (record);
        builder += "\n";
        
        return builder;
    }
    
    
    
    // MÉTHODES
    
    
    
    public static Logger createLogger ()
    {	
    	Logger logger = Logger.getLogger (FormatLog.class.getName ());
    	logger.setUseParentHandlers (false);

        FormatLog formatter = new FormatLog ();
        ConsoleHandler handler = new ConsoleHandler ();
        handler.setFormatter(formatter);

        logger.addHandler(handler);
        
    	return logger;
    }
}
