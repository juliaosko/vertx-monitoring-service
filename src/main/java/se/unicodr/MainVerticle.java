package se.unicodr;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

/**
 * This is the main verticle responsible for launching all
 * other verticles, i.e. the HttpServerVerticle, ServiceCheckerVerticle
 * and ServicePersistenceVerticle.
 *
 */
public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    Future<String> servicePersistenceVerticleDeployment = Future.future();
    vertx.deployVerticle(
      new ServicePersistenceVerticle(),
      servicePersistenceVerticleDeployment.completer());

    servicePersistenceVerticleDeployment.compose(result -> {

      Future<String> httpVerticleDeployment = Future.future();
      vertx.deployVerticle(
        HttpServerVerticle.class.getName(),
        new DeploymentOptions().setInstances(2),
        httpVerticleDeployment.completer());
      return httpVerticleDeployment;

    }).compose(result -> {
      Future<String> serviceCheckerDeployment = Future.future();
      vertx.deployVerticle(
        new ServiceCheckerVerticle(),
        serviceCheckerDeployment.completer());

      return serviceCheckerDeployment;
    }).setHandler(ar -> {
      if (ar.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(ar.cause());
      }
    });
  }

}
