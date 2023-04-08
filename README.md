# Important

- This library only works for static links, if the link request credentials for access to new links, it will not work
- This library only get future links to visit from the pages that share the domain name of first link

## How to use link checker

For use link checker dependency follow this steps

+ Include link-checker dependency on your project (https://mvnrepository.com/artifact/io.github.marcperez06/link-checker)
- For Maven:
```
<dependency>
    <groupId>io.github.marcperez06</groupId>
    <artifactId>link-checker</artifactId>
    <version>0.0.2</version>
</dependency>
```

- For Gradle:
```
implementation 'io.github.marcperez06:link-checker:0.0.2'
```

2. Call method `getReport()` or `getReports()` of LinkCheckerService
2.1 `getReport()`
- Using default configuration, using the default properties file (described on Properties section)
```
String url = "base url to check";
LinkCheckerReport report = LinkCheckerService.getReport(url);
```

- Using configuration from different properties file
```
String propertiesPath = "full properties path";
String url = "base url to check";
LinkCheckerReport report = LinkCheckerService.getReport(url, propertiesPath);

```

- Using default configuration from builder
```
String url = "base url to check";
LinkCheckerConfiguration configuration = new LinkCheckerConfigurationBuilder().build();
LinkCheckerReport report = LinkCheckerService.getReport(url, configuration);
```

- Creating an specific configuration using LinkCheckerConfigurationBuilder
```
String url = "base url to check";
LinkCheckerConfigurationBuilder configurationBuilder = new LinkCheckerConfigurationBuilder();
configurationBuilder.minDepth(1);
configurationBuilder.numThreads(2);
configurationBuilder.minInteractions(10);
configurationBuilder.minRequests(5);
configurationBuilder.sortNotFoundFirst(false);
LinkCheckerReport report = LinkCheckerService.getReport(url, configurationBuilder.build());

```

2.2 `getReports()`
- You can use the same methods on the examples above, but instead of use one url, use a List<String> as param
```
List<String> urls = new ArrayList<String>();
urls.add("https://www.nato.int/nato-welcome/index_es.html");
urls.add("https://www.fao.org/home/es");
LinkCheckerConfigurationBuilder configurationBuilder = new LinkCheckerConfigurationBuilder();
configurationBuilder.minDepth(1);
configurationBuilder.numThreads(3);
configurationBuilder.minRequests(5);
configurationBuilder.sortNotFoundFirst(false);
List<LinkCheckerReport> reports = LinkCheckerService.getReports(urls, configurationBuilder.build());
```

* Default configuration
```
min depth: 1
sort not found first: true
num threads: 1
```

## Properties

The properties configure when report will stop at reach the minimal number specified on each property enabled,
also defines the number of threads used for parallelization and if want the report of links visited sorted 
with the results NOT_FOUND in the first positions of report under `linksVisited` property

The default path for the properties is under project directory, following the path 

```
\resources\properties\link_checker.properties

``` 

- Properties Example:
```
link.checker.min.depth.enabled=true
link.checker.min.depth=1

link.checker.min.interactions.enabled=true
link.checker.min.interactions=100

link.checker.min.requests.enabled=true
link.checker.min.requests=100

link.checker.num.threads=3
link.checker.sort.not.found.first=true
```

## Example of Report

This Report have some info deleted for reduce his size
```
{
  "firstLink": "https://www.fao.org/home/es",
  "summaryBadLinks": [],
  "statistics": {
    "numInteractions": 1,
    "numRequests": 1,
    "numLinksVisited": 1,
    "numLinksNotVisited": 92,
    "numLinksCanNotChecked": 0,
    "numGoodLinks": 1,
    "numBadLinks": 0,
    "currentDepth": 0,
    "executionDurationInSeconds": 1
  },
  "linksVisited": {
    "https://www.fao.org/home/es": {
      "link": "https://www.fao.org/home/es",
      "status": "OK",
      "depth": 0,
      "entries": [],
      "exits": [
        "http://www.fao.org/home/es",
        "https://www.fao.org/contact-us/terms/es/"
      ]
    }
  },
  "linksNotVisited": [
    {
      "from": "https://www.fao.org/home/es",
      "to": "http://www.fao.org/home/es"
    },
    {
      "from": "https://www.fao.org/home/es",
      "to": "http://www.fao.org/about/en/"
    }
  ],
  "linksCanNotChecked": [],
  "summaryGoodLinks": [
    "https://www.fao.org/home/es"
  ],
  "configuration": {
    "minDepth": 0,
    "sortNotFoundFirst": true,
    "numThreads": 1
  }
}
```

## Examples
Can find different examples on unit test in github https://github.com/marcperez06/link-checker/tree/master/src/test/java/link_checker

## Github Project
https://github.com/marcperez06/link-checker

###### Author
Marc Pérez Rodríguez