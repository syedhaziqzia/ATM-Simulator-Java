package bank.management.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Database Connection Helper Class
 * Mocked for Haziq Zia's ATM Simulator (Runs without MySQL!)
 */
public class Conn {
    public Connection c;
    public java.sql.Statement s; // Keep for legacy if needed

    public Conn() {
        try {
            c = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[] { Connection.class },
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("prepareStatement")) {
                            return Proxy.newProxyInstance(
                                PreparedStatement.class.getClassLoader(),
                                new Class<?>[] { PreparedStatement.class },
                                new InvocationHandler() {
                                    public Object invoke(Object p, Method m, Object[] a) throws Throwable {
                                        if (m.getName().equals("executeQuery")) {
                                            return Proxy.newProxyInstance(
                                                ResultSet.class.getClassLoader(),
                                                new Class<?>[] { ResultSet.class },
                                                new InvocationHandler() {
                                                    boolean nextCalled = false;
                                                    public Object invoke(Object rsP, Method rsM, Object[] rsA) throws Throwable {
                                                        if (rsM.getName().equals("next")) {
                                                            boolean res = !nextCalled;
                                                            nextCalled = true;
                                                            return res;
                                                        }
                                                        if (rsM.getName().equals("getString")) {
                                                            if (rsA != null && rsA.length > 0 && rsA[0] instanceof String) {
                                                                String col = (String) rsA[0];
                                                                if (col.equals("type")) return "Deposit";
                                                                if (col.equals("amount")) return "1000";
                                                                if (col.equals("date")) return "2026-07-31";
                                                                if (col.equals("cardnumber") || col.equals("cardno")) return "1234567890123456";
                                                                if (col.equals("pin")) return "1234";
                                                            }
                                                            return "1234567890123456"; 
                                                        }
                                                        if (rsM.getReturnType() == int.class) return 0;
                                                        if (rsM.getReturnType() == boolean.class) return false;
                                                        return null;
                                                    }
                                                }
                                            );
                                        }
                                        if (m.getName().equals("executeUpdate")) {
                                            return 1;
                                        }
                                        if (m.getReturnType() == int.class) return 0;
                                        if (m.getReturnType() == boolean.class) return false;
                                        return null;
                                    }
                                }
                            );
                        }
                        if (method.getName().equals("createStatement")) {
                            return Proxy.newProxyInstance(
                                java.sql.Statement.class.getClassLoader(),
                                new Class<?>[] { java.sql.Statement.class },
                                new InvocationHandler() {
                                    public Object invoke(Object p, Method m, Object[] a) throws Throwable {
                                        if (m.getReturnType() == int.class) return 0;
                                        return null;
                                    }
                                }
                            );
                        }
                        if (method.getReturnType() == int.class) return 0;
                        if (method.getReturnType() == boolean.class) return false;
                        return null;
                    }
                }
            );
            // Mock statement just in case
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



