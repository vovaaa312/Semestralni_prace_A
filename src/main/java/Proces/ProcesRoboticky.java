package Proces;

public class ProcesRoboticky extends Proces{
    public ProcesRoboticky(String id, int cas) {
        super(id, cas);
    }

    @Override
    public String toString() {
        return "ROBO " + super.toString();
    }


}
