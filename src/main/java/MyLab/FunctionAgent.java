package MyLab;

import jade.core.Agent;
import java.util.concurrent.ThreadLocalRandom;

public class FunctionAgent extends Agent {

    @Override
    protected void setup() {

        String[] nName = new String[3], AgentNames = new String[3];
        AgentNames[0] = "RED";
        AgentNames[1] = "GREEN";
        AgentNames[2] = "BLUE";
        for (int i = 0; i < 3; i++)
            nName[i] = AgentNames[i%3];

        getArguments();
        addBehaviour(new MyBehaviour());
        if (this.getLocalName().equals("RED"))
            this.addBehaviour(new InitBehaviour(this, 3000, nName, RandomBetween(0, 10000), RandomBetween(0, 1000)));
        this.addBehaviour(new RecieveInit(this, nName, 0, 0));
        this.addBehaviour(new ReceiveMsgBehaviour(this));
    }

    public static double RandomBetween(double min, double max) {
        return (ThreadLocalRandom.current().nextDouble() * (max - min)) + min;
    }

}
