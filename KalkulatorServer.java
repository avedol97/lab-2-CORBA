import Kalkulator.KalkulatorInterface;
import Kalkulator.KalkulatorInterfaceHelper;
import Kalkulator.KalkulatorInterfacePOA;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

class KalkulatorInterfaceImpl extends KalkulatorInterfacePOA {
    private ORB orb;//prywatny obiekt orb

    public void setORB(ORB orb_val) {
        orb = orb_val; //będzie służył do wywołania metody shutdown()
    }

    //implementacja metod możliwych do wywołania przez obiekt klienta
    public String add(double x, double y) //implementacja metody „add”
    {
        double res = x + y;
        return Double.toString(res);
    }


    public String addComplexNumbers(double x1Re, double x1Im,double x2Re, double x2Im)
    {
        String real = Double.toString(x1Re + x2Re);
       String imag = Double.toString(x1Im + x2Im);

       return real+" + "+imag+"i";
    }

    public String subtractComplexNumbers(double x1Re, double x1Im,double x2Re, double x2Im)
    {
        String real = Double.toString(x1Re - x2Re);
        String imag = Double.toString(x1Im - x2Im);

        return real+" + "+imag+"i";
    }
    public String multiplyComplexNumbers(double x1Re, double x1Im,double x2Re, double x2Im)
    {
        String real = Double.toString(x1Re + x2Re - x1Im * x2Im);
        String imag = Double.toString(x1Im * x2Re + x1Re * x2Im);

        return real+" + "+imag+"i";
    }
    public String divideComplexNumbers(double x1Re, double x1Im,double x2Re, double x2Im)
    {
        String real = Double.toString((x1Re * x2Re + x1Im * x2Im)/(x2Re * x2Re + x2Im + x2Im));
        String imag = Double.toString((x1Im * x2Re * x1Re * x2Im)/(x2Re * x2Re + x2Im + x2Im));

        return real+" + "+imag+"i";
    }

    public String subtract(double x, double y) //implementacja metody „subtract”
    {
        double res = x - y;
        return Double.toString(res);
    }

    public String multiply(double x, double y) //implementacja metody „multiply”
    {
        double res = x * y;
        return Double.toString(res);
    }

    public String divide(double x, double y) //implementacja metody „divide”
    {
        double res = x / y;
        return Double.toString(res);
    }

    public String random() //implementacja metody „random”
    {
        double res = Math.random();
        return Double.toString(res);
    }

    public void shutdown() //implementacja metody „shutdown”
    {
        orb.shutdown(false);
    }
}


public class KalkulatorServer {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null); //utworzenie i inicjalizacja ORB
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate(); //referencja do POA (skeletonu)
//stworzenie obiektu usługowego
            KalkulatorInterfaceImpl KalkulatorImpl = new KalkulatorInterfaceImpl();
//rejestracja obiektu w ORB
            KalkulatorImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(KalkulatorImpl);
            KalkulatorInterface href = KalkulatorInterfaceHelper.narrow(ref);
//utworzenie kontekstu nazw
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//związanie obiektu z nazwą
            String name = "KalkulatorOperations";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);
            System.out.println("\n----------------------------------------------");
            System.out.println("Kalkulator: Gotowy i czeka...");
            System.out.println("\n----------------------------------------------");
            orb.run();
        } catch (Exception e) {
//System.err.println("ERROR: " + e);
//e.printStackTrace(System.out);
            System.out.println("\n----------------------------------------------");
            System.out.println("Kalkulator: BLAD !");
            System.out.println("\n----------------------------------------------");
        }
        System.out.println("\nKalkulator: Wyjscie...");
        System.out.println("\n----------------------------------------------");
    }
}
