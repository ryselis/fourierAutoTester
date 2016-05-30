package edu.ktu.stuliene;

/**
 * Created by Aiste on 2016.05.16.
 */
public class Oracle {

    public boolean checkResult(Object[] data, Object result, boolean exceptionOccured) {

        if (exceptionOccured){
            Object[] objects = (Object[]) data[0];
            return (objects.length % 2 != 0);
        }

        if (exceptionOccured){
            Object[] objects = (Object[]) data[0];
            return ((objects.length & (objects.length - 1)) != 0);
        }

        for (Object o : data) {
            if (o.getClass() != result.getClass()) {
                return false;
            }
        }

        for (Object o : data) {
            Object[] objects = (Object[]) o;
            Object[] results = (Object[]) result;
            if(objects == null && results != null)
                return false;
            if(exceptionOccured && ((objects.length & (objects.length - 1)) == 0)) {
                return false;
            }
            if(!exceptionOccured && (results == null)){
                return false;
            }
            if(exceptionOccured && (results.length > 0)){
                return false;
            }
            if(!exceptionOccured && ((objects.length & (objects.length - 1)) != 0)) {
                return false;
            }
            if(!exceptionOccured && (objects.length > 0) && (results.length == 0)){
                return false;
            }
        }

        for (Object o : data) {
            Object[] objects = (Object[]) o;
            Object[] results = (Object[]) result;
            if ((objects.length > 1) && (objects.length % 2 != 0) && (results.length > 0))
                return false;
            if ((objects.length > 1) && (objects.length % 2 == 0) && (results.length != objects.length))
                return false;
            if((objects.length == 1) && (results.length != 1))
                return false;
            if((objects.length == 0) && (results.length != 0))
                return false;
            if((results.length > 1) && (results.length % 2 != 0))
                return false;
            if((results.length & (results.length - 1)) != 0)
                return false;
            if((objects.length & (objects.length - 1)) != 0)
                return false;

            try {
                double dat = Double.parseDouble(objects[0].toString().split("E")[0]);
                double rez = Double.parseDouble(results[0].toString().split("E")[0]);
                double eps = Math.pow(10, -16);
                if((objects.length == 1) && (Math.abs(Math.abs(dat)- Math.abs(rez)) > eps))
                    return false;
                if((objects.length != 1) && ((Math.abs(Math.abs(dat)- Math.abs(rez)) < eps)))
                    return false;
            } catch (NullPointerException e){
                return false;
            } catch (NumberFormatException e){
                return false;
            }

        }

        return true;

    }

}
