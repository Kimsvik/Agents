package MyLab;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SpamMsgBehaviour extends TickerBehaviour {
    private String[] neighbourAgent;
    private Agent myAgent;
    private int i = 0;
    public SpamMsgBehaviour(Agent a, long period, String[] neighbourAgent) {
        super(a, period);
        this.myAgent = a;
        this.neighbourAgent = neighbourAgent;
    }

    @Override
    protected void onTick() { // Действия, которые совершаются с определенной частотой, в данном случае опрос раз в 3000 мс
        if (neighbourAgent[i % 3].equals(myAgent.getLocalName())) {
            i++;
            return;
        }
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); // Определяем тип сообщений, которые будут отправляться спамером
        AID n = new AID(neighbourAgent[i % 3], false); // Идентификатор агента, которому мы отправили сообщение
        msg.addReceiver(n); // Добавляем получателя сообщения
        msg.setContent("Hello from " + myAgent.getLocalName() + " to " + n.getLocalName()); // Тело сообщения
        myAgent.send(msg);
        i++;
    }

    @Override
    public int onEnd() { // Описывается поведение при окончании работы
        System.err.println("Count > 5" + " I DID MY WORK, BRUH ");
        return super.onEnd();
    }
}