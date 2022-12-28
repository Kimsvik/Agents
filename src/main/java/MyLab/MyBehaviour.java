package MyLab;

import jade.core.behaviours.OneShotBehaviour;

public class MyBehaviour extends OneShotBehaviour {
    private boolean finish;

    @Override
    public void action() {
        System.out.println("Agent " + getAgent().getLocalName() + " is here!");
        finish = true;
    }
}
