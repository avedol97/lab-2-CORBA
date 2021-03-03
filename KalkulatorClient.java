import Kalkulator.KalkulatorInterface;
import Kalkulator.KalkulatorInterfaceHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class KalkulatorClient {
    static KalkulatorInterface KalkulatorImpl;

    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "KalkulatorOperations";
            KalkulatorImpl = KalkulatorInterfaceHelper.narrow(ncRef.resolve_str(name));
            System.out.println("\nKalkulatorClient: Dodawanie\t= " + KalkulatorImpl.addComplexNumbers(1,2,3,4));
            System.out.println("\nKalkulatorClient: Odejmoanie\t= " + KalkulatorImpl.subtractComplexNumbers(1,2,3,4));
            System.out.println("\nKalkulatorClient: Mnozenie\t= " + KalkulatorImpl.multiplyComplexNumbers(1,2,3,4));
            System.out.println("\nKalkulatorClient: Dzielenie\t= " + KalkulatorImpl.divideComplexNumbers(1,2,3,4));
            KalkulatorImpl.shutdown();
        } catch (Exception e) {
        }
    }
}
