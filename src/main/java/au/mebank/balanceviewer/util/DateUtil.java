package au.mebank.balanceviewer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    final static Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    public static Date getDate(String dateString) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            LOG.error("Parsing exception date {} reason {}", dateString, e.getMessage());
        }
        return null;
    }

}
