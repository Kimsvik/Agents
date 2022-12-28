package MyLab;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMsgBehaviour extends Behaviour {
    private Agent myAgent;
    private int count = 0;
    private MessageTemplate mt;

    public ReceiveMsgBehaviour(Agent myAgent) {
        this.myAgent = myAgent;
    }


    @Override
    public void onStart() { // Описывает поведение при старте агента
        mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST); //Могу получать сообщения типа INFORM
    }

    @Override
    public void action() { //Тут описывается то, что должен делать агент
        ACLMessage msg = myAgent.receive(mt); // метод получения данных, причем mt -получаем при старте и фильтруем сообщения
        if(msg != null){ // Если сообщение не нулевое, то делаем
            String[] str = msg.getContent().split(" - ");
            int X = Integer.parseInt(str[0]);
            int delta = Integer.parseInt(str[1]);
            System.out.println("I got "+str+" from "+msg.getSender().getLocalName());
            ACLMessage prp = new ACLMessage(ACLMessage.PROPOSE); // Определяем тип сообщений, которые будут отправляться спамером
            AID n = new AID(msg.getSender().getLocalName(), false); // Идентификатор агента, которому мы отправили сообщение
            prp.addReceiver(n); // Добавляем получателя сообщения
            myAgent.send(msg);
            switch (myAgent.getLocalName()) {
                case "RED":
                    prp.setContent(CalcRed(X-delta)+ " - "+CalcRed(X)+" - "+CalcRed(X+delta)); // Тело сообщения
                    break;
                case "GREEN":
                    prp.setContent(CalcGreen(X-delta)+ " - "+CalcGreen(X)+" - "+CalcGreen(X+delta)); // Тело сообщения
                    break;
                case "BLUE":
                    prp.setContent(CalcBlue(X-delta)+ " - "+CalcBlue(X)+" - "+CalcBlue(X+delta)); // Тело сообщения
                    break;
            }
        } else {
            block(); // Блокировка агента
        }
    }

    @Override
    public int onEnd() { // Описывается поведение при окончании работы
        System.err.println("Count > 5" + " I DID MY WORK, BRUH ");
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return count > 5;
    }// Тут описывается, при каких условиях это поведение должно кончится
    // Можно вернуть true и  будет работать всегда

    public String CalcRed(double x) {
        return String.valueOf(-0.5*x*x-4);
    }

    public String CalcGreen(double x) {
        return String.valueOf(Math.pow(2, -0.1*x));
    }

    public String CalcBlue(double x) {
        return String.valueOf(Math.cos(x));
    }

}
