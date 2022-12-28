package MyLab;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SendMsg extends Behaviour  {
    private String nextAgent;

    public SendMsg (String nextAgent) {
        this.nextAgent = nextAgent;
    }
    @Override
    public void action() {
        // принял икс и дельту, распрарсил и отправил уже численное значение в принятие очереди
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        AID n = new AID(nextAgent, false);
        msg.addReceiver(n); // Добавляем получателя сообщения
        msg.setContent("Next initiator is: "+nextAgent); // Тело сообщения
        myAgent.send(msg);
        myAgent.removeBehaviour(this);
    }

    @Override
    public boolean done() {
        return false;
    }
}
