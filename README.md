# little-crawler

Simple crawler which visits pages within the provided domain.

It generates custom site map which includes:
- links to pages within the provided domain
- links to static content (e.g. images) for each respective page
- links to external URLs

# Building
## Requirements
- Java 11 JDK
## Command
```
mvn package
```

# Running
## Command
```
java -jar little-crawler.jar --starting.url=http://example.com
```
## Output
Output is saved to `sitemap.txt`.
### Example
```
internal-links:
  url: https://example.com
    static-content: https://example.com/logo.jpg
  url: https://blog.example.com
  url: https://shop.example.com
external-links:
  url: https://www.facebook.com
  url: https://www.twitter.com
```
# Development
## Requirements
- Lombok support enabled in the IDE
# Notes
## Possible improvements
- find or develop better/faster crawling engine than crawl4j
- support multiple output formats (e.g. XML, YAML, JSON)
- add command line parameters for:
  - output file name
  - output format
  - verbose mode
- improve input validation
- add more logging
- add more tests
- make sure that images with relative path are also processed