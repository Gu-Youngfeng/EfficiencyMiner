# EfficiencyMiner


[![GitHub license](https://img.shields.io/github/license/Gu-Youngfeng/EffciencyMinner.svg)](https://github.com/Gu-Youngfeng/EffciencyMinner/blob/master/LICENSE) 
![GitHub license](https://img.shields.io/badge/Version-0.0.1--SNAPSHOT-orange.svg)


### 1. Modules

- **cn.edu.whu.cstar.timer** module calculates the average execution time of analysis and prediction of newly-submited crash. This process has the same results with my another prototype [CraTer](https://github.com/Gu-Youngfeng/CraTer) (note that CraTer uses spoon 5.X, while EffciencyMinner uses spoon 6.X).

- **cn.edu.whu.cstar.efforter** module calculates the line efforts when apply analysis and prediction on a newly-submitted crash, there are 4 kinds of line efforts calculation ways, including `Loc of all methods appear in Stack Trace`, `Size of frames in Stack Trace`, `Size of frames till the fault in Stack Trace`, `Loc of methods in Stack Trace till the fault`.

- **cn.edu.whu.cstar.typer** module counts the different exception types that one mutator has generated in each project, the default 7 mutators are from pit tool.

- **cn.edu.whu.cstar.simulator** module simulates the process of dataset generation, randomization, and stratification. It can return the crashes' original index, which appears in generated dataset.

- **cn.edu.whu.cstar.evaluation** module provides a way to conduct model building and class prediction.

### 2. Tools

- **spoon**, which is a useful static program analysis tool, see [http://spoon.gforge.inria.fr/](http://spoon.gforge.inria.fr/).
- **junit4**, which is a popular testing framework, see [https://junit.org/junit4/](https://junit.org/junit4/).
- **pit**, which is a novel java mutation tool, as well as a test coverage counter, see [http://pitest.org/](http://pitest.org/). 

### 3. Inputs

- **crash reports of each project**, which collects all stack trace of one project. Note that each stack trace also contains the mutation position.
- **parent project**, which includes 7 real-world projects. We need source code as well as the jar file which should be added into classpath.
- **mutation information**, which records all detailed mutation information of each mutants by pit tool.  
