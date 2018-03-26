# BASM

> Because sometimes writing java isn't enough.

## Index
* Description
* Motivation
* Quick Start
    * Build Instructions
    * Commandline Parameters.
    * Transformers
* Example Outputs
    * BinarySearch
    
## Description

**BASM** currently is a bytecode editing library in it's early stages.

## Motivation

Why did I write yet another tool to edit bytecode?
I was reading this: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
and simply wanted to gain an even better understanding of how bytecode works.

## Quick Start

#### Build Instructions

```
mvn package
```

#### Commandline Parameters.


|   Parameter   |                               Description                                 |
|:--------------|:--------------------------------------------------------------------------|
| -in           | The file which should be transformed.                                     |
| -out          | The file the output file should be written to.                            |
| -transformer  | The class of the transformer responsible for transformation of the input. |

#### Transformers

Transformers are useful examples of how to use **BASM**.
One can tell their purpose by looking at their name "Transformer".
They take in input and transform it into useful output.
Currently the most useful Transformer seems to be the Disassembler, I have provided some usage example and it's output below.

## Example Outputs

```
java -jar BASM.jar -in BinarySearch.class -transformer com.nur1popcorn.basm.transformers.Disassembler
```

#### Binary Search:
```
public binarySearch([II)I:
    iconst_0
    istore_3
    aload_1
    arraylength
    iconst_1
    isub
    istore 4
  L0
    iload_3
    iload 4
    if_icmpgt L1
    iload_3
    iload 4
    iadd
    iconst_1
    iushr
    istore 5
    aload_1
    iload 5
    iaload
    istore 6
    iload 6
    iload_2
    if_icmple L2
    iload 5
    iconst_1
    isub
    istore 4
    goto L3
  L2
    iload 6
    iload_2
    if_icmpge L4
    iload 5
    iconst_1
    iadd
    istore_3
    goto L3
  L4
    iload 5
    ireturn
  L3
    goto L0
  L1
    iconst_m1
    ireturn
```
