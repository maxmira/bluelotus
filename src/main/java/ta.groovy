import groovy.json.JsonSlurper
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub


def slurper = new JsonSlurper()
Jedis jedis = new Jedis("localhost");
JedisPubSub ps = new JedisPubSub() {

    @Override
    void onMessage(String channel, String message) {
        def result = slurper.parseText(message)
        println(channel+ ': '+ result['270']);
    }

    @Override
    void onPMessage(String pattern, String channel, String message) {
        println(pattern+": "+message)
    }

    @Override
    void onSubscribe(String channel, int subscribedChannels) {
        println(channel+" subscribed")
    }

    @Override
    void onUnsubscribe(String channel, int subscribedChannels) {
        println(channel+" unsub")
    }

    @Override
    void onPUnsubscribe(String pattern, int subscribedChannels) {
        println(pattern+" Unsub")
    }

    @Override
    void onPSubscribe(String pattern, int subscribedChannels) {
        println(pattern+" sub")
    }
}

jedis.subscribe(ps,"EUR/USD")


