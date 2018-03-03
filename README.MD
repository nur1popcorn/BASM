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
public binarySearch([II)I {
    0:  iconst_0
    1:  istore_3
    2:  aload_1
    3:  arraylength
    4:  iconst_1
    5:  isub
    6:  istore $4
    8:  iload_3
    9:  iload $4
    b:  if_icmpgt $0 $34
    e:  iload_3
    f:  iload $4
    11: iadd
    12: iconst_1
    13: iushr
    14: istore $5
    16: aload_1
    17: iload $5
    19: iaload
    1a: istore $6
    1c: iload $6
    1e: iload_2
    1f: if_icmple $0 $c
    22: iload $5
    24: iconst_1
    25: isub
    26: istore $4
    28: goto $0 $14
    2b: iload $6
    2d: iload_2
    2e: if_icmpge $0 $b
    31: iload $5
    33: iconst_1
    34: iadd
    35: istore_3
    36: goto $0 $6
    39: iload $5
    3b: ireturn
    3c: goto $ff $cc
    3f: iconst_m1
    40: ireturn
}

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
