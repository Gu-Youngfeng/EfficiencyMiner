# EffciencyMinner


[![GitHub license](https://img.shields.io/github/license/Gu-Youngfeng/EffciencyMinner.svg)](https://github.com/Gu-Youngfeng/EffciencyMinner/blob/master/LICENSE) 
![GitHub license](https://img.shields.io/badge/Version-0.0.1--SNAPSHOT-orange.svg)


### 1. Modules

- **cn.edu.whu.cstar.timer** module calculates the average execution time of analysis and prediction of newly-submited crash. This process has the same results with my another prototype [CraTer](https://github.com/Gu-Youngfeng/CraTer) (note that CraTer uses spoon 5.X, while EffciencyMinner uses spoon 6.X).

- **cn.edu.whu.cstar.efforter** module calculates the reduced efforts (in terms of lines of code) when apply analysis and prediction on a newly-submitted crash.

- **cn.edu.whu.cstar.typer** module counts the different exception types that one mutator has generated, the default mutators are from pit tool.

### 2. Tools

- **spoon**, which is a useful static program analysis tool, see [http://spoon.gforge.inria.fr/](http://spoon.gforge.inria.fr/).
- **junit4**, which is a popular testing framework, see [https://junit.org/junit4/](https://junit.org/junit4/).
- **pit**, which is a novel java mutation tool, as well as a test coverage counter, see [http://pitest.org/](http://pitest.org/). 

### 3. Inputs

- **crash reports of each project**, which collects all stack trace of one project. Note that each stack trace also contains the mutation position.
- **parent project**, which includes 7 real-world projects. We need source code as well as the jar file which should be added into classpath.
- **mutation information**, which records all detailed mutation information of each mutants by pit tool.  
