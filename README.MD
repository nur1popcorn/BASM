# BASM

> Because sometimes writing java isn't enough.

## Index
* Quick Start
    * Build instructions
    * Usage examples
* Example Outputs
    * BinarySearch
## Quick Start

#### Build instructions

```
sudo apt install maven
mvn package
```

#### Usage examples

`java -jar BASM.jar -in BinarySearch.class -out BinarySearch.basm`

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
