package perobobbot.server.web.controller;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class TaskSchedulers {

    public static final Scheduler DB = Schedulers.boundedElastic();
}
