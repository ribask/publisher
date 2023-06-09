# publisher

The overall idea of the project is to develop some studies on data streaming using
sqs and redis in order to provide data to clients as fast as possible. The overall architecture is better
described in the image below:

![Alt text](misc/publisher.png "Main concept")

# requirements

To run the project locally, it's needed a redis and to a sqs connection. In order to make it simpler to run it locally,
it is possible to use the docker shell script "docker/start.sh" in order to both start the localstack and redis containers.

# current test coverage

84% classes, 68% lines covered