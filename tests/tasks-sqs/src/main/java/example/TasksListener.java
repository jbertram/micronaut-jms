package example;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.jms.Session;
import java.util.concurrent.atomic.AtomicInteger;

import static io.micronaut.jms.sqs.configuration.SqsConfiguration.CONNECTION_FACTORY_BEAN_NAME;

@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class TasksListener {

    private static final Logger LOG = LoggerFactory.getLogger(TasksListener.class);

    public static final AtomicInteger TASKS_PROCESSED = new AtomicInteger();

    @Queue(value = TaskConstants.FIFO_QUEUE, concurrency = "1-1")
    public void receive(@MessageBody Task task) {
        LOG.info("Received task with id: {}", task.getId());
        TASKS_PROCESSED.incrementAndGet();
    }

}
