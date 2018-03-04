# BASM

> Because sometimes writing java isn't enough.

## Index
* Quick Start
    * Build instructions
    * Usage example
    * Transformers
* Example Outputs
    * BinarySearch
## Quick Start

#### Build instructions

```
sudo apt install maven
mvn package
```

#### Usage example

`java -jar BASM.jar -in {CLASS} -out {OUT FILE} -transformer {TRANSFORMER}`

#### Transformers

|     Transformer    |                       Description                          |                     Class                    |
|:-------------------|:-----------------------------------------------------------|:---------------------------------------------|
|Assembler           |Not Implemented                                             |com.nur1popcorn.basm.transformers.Assembler   |
|Decompiler          |Not Implemented                                             |com.nur1popcorn.basm.transformers.Decompiler  |
|Disassembler        |Turns the given class file into it's bytecode representation.|com.nur1popcorn.basm.transformers.Disassembler|
|MaliciousCodeScanner|Kinda works but not really well.|com.nur1popcorn.basm.transformers.MaliciousCodeScanner|

## Example Outputs

#### BinarySearch:
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
