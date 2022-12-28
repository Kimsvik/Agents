package MyLab;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Arrays;

public class InitBehaviour extends TickerBehaviour {
    private String[] neighbourAgent;
    private Agent myAgent;
    private double X;
    private double delta;
    private double sumXminus = 0, sumX = 0, sumXplus = 0;
    private MessageTemplate mt;
    private int step;
    private double res = 0;
    private int repliesCount = 0;

    public InitBehaviour(Agent a, long period, String[] neighbourAgent, double X, double delta) {
        super(a, period);
        this.myAgent = a;
        this.neighbourAgent = neighbourAgent;
        this.X = X;
        this.delta = delta;
    }

    @Override
    public void onStart() { // Описывает поведение при старте агента
        mt = MessageTemplate.or( //Метод обработки сообщений, то есть через MessageTemplate можно делать фильтр
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST), //Могу получать сообщения типа REQUEST
                MessageTemplate.MatchPerformative(ACLMessage.INFORM) //Могу получать сообщения типа INFORM
        );
        step = 0;
    }

    @Override
    protected void onTick() {
//        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); // Определяем тип сообщений, которые будут отправляться спамером
//        for (int i = 1; i < 3; i++) {
//            AID n = new AID(neighbourAgent[i], false);
//            msg.addReceiver(n); // Добавляем получателя сообщения
//            msg.setConversationId("calculation");
//            msg.setContent(String.valueOf(X + " - " + delta)); // Тело сообщения
//            msg.setReplyWith("msg" + System.currentTimeMillis());
//            myAgent.send(msg);
//        }
//        AID n1 = new AID(neighbourAgent[1], false); // Идентификатор агента, которому мы отправили сообщение
//        msg.addReceiver(n1); // Добавляем получателя сообщения
//        msg.setContent(String.valueOf(X - delta)); // Тело сообщения
//        myAgent.send(msg);
//
//        AID n2 = new AID(neighbourAgent[2], false); // Идентификатор агента, которому мы отправили сообщение
//        msg.addReceiver(n2); // Добавляем получателя сообщения
//        msg.setContent(String.valueOf(X + delta)); // Тело сообщения
//        myAgent.send(msg);
//
//        block();

//        ACLMessage m = myAgent.receive(mt); // метод получения данных, причем mt -получаем при старте и фильтруем сообщения
//        if (m != null) { // Если сообщение не нулевое, то делаем
//            System.out.println(m.getContent());
//        } else {
//            block(); // Блокировка агента
//        }
        switch (step) {
            case 0:
// Отправляем запросы агентам
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                for (int i = 0; i < neighbourAgent.length; i++) {
                    AID n = new AID(neighbourAgent[i], false);
                    msg.addReceiver(n); res = delta;
                }
                msg.setContent(X + " - " + delta);
                msg.setConversationId("calculation");
                System.out.println("Result is " + res);
                msg.setReplyWith("msg" + System.currentTimeMillis()); // Unique value
                myAgent.send(msg);
// Готовимся получать ответы
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("calculation"),
                        MessageTemplate.MatchInReplyTo(msg.getReplyWith()));
                step = 1;
//                int repliesCount = 0;
                sumXminus = 0;
                sumX = 0;
                sumXplus = 0;
                System.out.print(res);
                break;

            case 1:
// Получаем все ответы от агентов
                ACLMessage reply = myAgent.receive(mt);
                if (reply != null) {
// Ответ получен
                    if (reply.getPerformative() == ACLMessage.PROPOSE) {
// Суммируем результат
                        String[] str = reply.getContent().split(" - ");
                        sumXminus += Integer.parseInt(str[0]);
                        sumX += Integer.parseInt(str[1]);
                        sumXplus += Integer.parseInt(str[2]);
                    }
                    repliesCount++;
                    if (repliesCount >= neighbourAgent.length) {
// Мы получили все ответы
                        step = 2;
                    }
                } else {
                    block();
                }
                break;
            case 2:
// Определяем новые переменные
                if (sumXminus < sumX && sumXminus < sumXplus)
                    X = X - delta;
                if (sumX < sumXminus && sumX < sumXplus)
                    delta /= 2;
                if (sumXplus < sumX && sumXplus < sumXminus)
                    X = X + delta;
                myAgent.removeBehaviour(this);
                String nextAgent = neighbourAgent[getRandomNumber(0, 2)];
                myAgent.addBehaviour(new SendMsg(nextAgent)); // Передать значения икс и дельта в числах
                step = 3;
        }
    }

// Готовимся менять инициатора
//        case 3:
//// Receive the purchase order reply
//        remoteBehaviou();
//        AID n = new AID(neighbourAgent[1], false);
//        n.
//        if (reply != null) {
//// Purchase order reply received
//            if (reply.getPerformative() == ACLMessage.INFORM) {
//// Purchase successful. We can terminate
//                myAgent.doDelete();
//            }
//            step = 4;
//        } else {
//            block();
//        }
//        break;
//    }

//    public boolean done() {
//        return (step == 20);
//    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
