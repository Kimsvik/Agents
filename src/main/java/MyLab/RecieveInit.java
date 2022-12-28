package MyLab;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.concurrent.ThreadLocalRandom;

import static jade.lang.acl.MessageTemplate.MatchPerformative;

public class RecieveInit extends Behaviour {
    private MessageTemplate mt;
    private String[] nName;
    private double curX;
    private double curDelta;

    public RecieveInit(Agent myAgent, String[] nName, double curX, double curDelta) {
        this.myAgent = myAgent;
        this.nName = nName;
        this.curX = curX;
        this.curDelta = curDelta;
    }

    @Override
    public void action() {
        mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM); //Могу получать сообщения типа INFORM
        ACLMessage msg = myAgent.receive(mt); // метод получения данных, причем mt -получаем при старте и фильтруем сообщения
        if(msg != null){ // Если сообщение не нулевое, то делаем
            System.out.println("I'm "+myAgent.getLocalName()+" and i received Init with content "+msg.getContent() + " from "+msg.getSender().getLocalName());
            myAgent.addBehaviour(new InitBehaviour(this.getAgent(), 3000, nName, curX, curDelta));
        } else {
            block(); // Блокировка агента
        }
    }

    @Override
    public boolean done() {
        return false;
    }

}
