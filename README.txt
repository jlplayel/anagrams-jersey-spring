ANAGRAM JERSEY SPRING
---------------------

DESCRIPTION
Provided a <phrase> resource is going to response the list of possible anagrams.
Internally it uses a file with a list of words as dictionary (around 50000).
It looks for words with a minimum of 3 letters in English language.
Spaces or any other symbol non-letter is not considered.
There are two different ways implemented, called Java and Sql.
- The Java approach uses pure Java 8 for getting the anagrams.
- The Sql approach uses an in-memory database for getting the solution.

PERFORMANCE
It is reasonable for a "normal" computer if the phrase does not have many letters.
It depends of the letters but around 10 letters should get a result in short time.


REQUIREMENT
- Install Java 8 (lower versions do not work)
- Install Gradle ( it was used 3.2.1, wrapper is available)
- Install any server (Tomcat, Jetty, any server should work for this project)
	Right now the project does not have any embedded server.
- Optional: Eclipse with Gradle plugging and/or a configured served.

GRADLE COMMANDS
-  $ gradle build : generates the WAR file, ready for be included in the server.
-  $ gradle test : runs the unitary test
-  $ gradle integrationTest : runs the integration test
- Optional(for preparing to Eclipse): $ gradle cleaneclipse eclipse

DEPENDENCIES
Check the build.graddle file for a full list of dependencies and their versions.


LOCAL URLs
- http://localhost:8080/rest/javasolution/anagrams/<Phrase>
- http://localhost:8080/rest/sqlsolution/anagrams/<Phrase>
(The ".../rest/..." part is configured in server, in Tomcat is in Web Modules part.
 Probably by default is going to be instead ".../anagram/..." for rootProject.name in graddle)

 
EXAMPLES
http://localhost:8080/rest/javasolution/anagrams/IT-Crowd
<anagrams>
	<resultNum>5</resultNum>
	<timeMillisec>35</timeMillisec>
	<phrase>tic word</phrase>
	<phrase>cord wit</phrase>
	<phrase>doc writ</phrase>
	<phrase>cow dirt</phrase>
	<phrase>cod writ</phrase>
</anagrams>


http://localhost:8080/rest/javasolution/anagrams/Monterreal!
<anagrams>
	<resultNum>286</resultNum>
	<timeMillisec>2439</timeMillisec>
	<phrase>men relator</phrase>
	<phrase>eternal rom</phrase>
	<phrase>learnt rome</phrase>
	<phrase>leaner mort</phrase>
	...
	<phrase>lam rent roe</phrase>
</anagrams>


FOR DEVELOPERS
In general the code is "clean" (always can be better) and possible improvements could be:
- To handle exceptions.
- To adapt the 3 letters restriction to a more flexible use, right now it is hard-coded.
- To adapt to i18n, right now it is going to work only with English alphabet.
- To adapt to different dictionaries.
- The algorithms can be improved for better performance.
  For example, make the first restriction to words with the needed letters can be extended.
- The timing of the solution could be included in a filter.
- There are some functions included in static classes.
  More standard approach could be add them as private method in the service or create an independent library for them.
...
 






 
