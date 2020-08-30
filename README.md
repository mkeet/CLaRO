# CLaRO
**C**ompetency question **La**nguage for specifying **R**equirements for an **O**ntology and similar artefacts

## Introduction
Competency Questions (CQs) for an ontology aim to demarcate the scope of its contents. They are  sparsely used in ontology development, however, likely because there is no tooling and automation for writing them and evaluating them against the ontology, therewith hampering their effective use. Regarding support for authoring CQs, there are a few question templates based on informal analyses of a small number of competency questions, hence, with limited coverage of question types and sentence constructions. No Controlled Natural Language exists to guide the domain experts to author CQs. 

We aim to fill this gap by proposing the CLaRO template-based Controlled Natural Language  resource to author CQs. For its design, we exploited a [new dataset of 234 TBox-level CQs that had been analysed automatically into 106 patterns](https://github.com/CQ2SPARQLOWL/Dataset), which we analysed on their shape, commonalities, and recurring patterns, which was subsequently used to design a template-based CNL, with an additional XML serialisation. The templates also are annotated with a set of human-friendly aspects so that a user may select, e.g., "a CQ template that asks for difference" to narrow down formulating a CQ. 

The CNL was evaluated with a subset of questions from the original dataset and with two sets of newly sourced competency questions. The coverage of CLaRO, with its 93 main templates and 41 linguistic variants, is about 90% for unseen questions. CLaRO has the potential to facilitate streamlining formalising ontology content requirements and, given that about one third of the competency questions in the test sets turned out to be invalid questions, assist in writing good questions.  

## Resources on this github repo
This repository has the following resources:
- the 134 templates of CLaRO, in xml and in txt
- the data and results of the CLaRO evaluation
- a data file with annotations how CLaRO was created
- a tool to author questions using the CLaRO templates

## Citing CLaRO

Keet, C.M., Mahlaza, Z., Antia, M.-J. CLaRO: a [Controlled Language for Authoring Competency Questions](http://www.meteck.org/files/MTSR2019CLaRO.pdf). 13th Metadata and Semantics Research Conference (MTSR'19). E. Garoufallou et al. (Eds.). Springer CCIS vol. 1057, 3-15. 28-31 Oct 2019, Rome, Italy. [published version at Springer](https://link.springer.com/chapter/10.1007/978-3-030-36599-8_1)

## Availability for use and reuse
CLaRO is available under a CC-BY licence
