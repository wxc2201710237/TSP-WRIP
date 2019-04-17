package TSP.Parallel;

import TSP.Route;
import TSP.algorithm.ConstantSizeRouteArchive;
import TSP.algorithm.RouteArchive;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ArchiveInsertVertexTask extends RecursiveAction {
    ConstantSizeRouteArchive archive;
    List<Route> routes;
    int vertexId;
    private static final int THRESHOLD = 100;
    private static final int TASK_COUNT = 10;

    public ArchiveInsertVertexTask(ConstantSizeRouteArchive archive, List<Route> routes, int vertexId) {
        this.archive = archive;
        this.routes = routes;
        this.vertexId = vertexId;
    }

    @Override
    protected void compute() {
        if (routes.size() < THRESHOLD) {
            for (Route route : routes) {
                for (Route r : route.insert(vertexId)) {
                    archive.put(r);
                }
            }
        } else {
            ArchiveInsertVertexTask[] tasks = new ArchiveInsertVertexTask[TASK_COUNT];
            int taskSize=routes.size()/TASK_COUNT;
            if(routes.size()%TASK_COUNT!=0){
                taskSize++;
            }

            for (int i = 0; i < routes.size(); i+=taskSize) {
                int begin=i;
                int end=Math.min(routes.size(),i+taskSize);
                tasks[i/taskSize]=new ArchiveInsertVertexTask(this.archive, routes.subList(begin, end), vertexId);
            }

            invokeAll(tasks);
        }
    }
}
