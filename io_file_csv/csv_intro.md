---
sort: 1
---

# CSV 文件格式

逗号分隔值（Comma-Separated Values，CSV，有时也称为字符分隔值，因为分隔字符也可以不是逗号），其文件以纯文本形式存储表格数据（数字和文本）。纯文本意味着该文件是一个字符序列，不含必须像二进制数字那样被解读的数据。CSV 文件由任意数目的记录组成，记录间以某种换行符分隔；每条记录由字段组成，字段间的分隔符是其它字符或字符串，最常见的是逗号或制表符。通常，所有记录都有完全相同的字段序列。

CSV 文件格式的通用标准并不存在，但是在 [RFC 4180](https://tools.ietf.org/html/rfc4180) 中有基础性的描述。使用的字符编码同样没有被指定，但是 7-bit ASCII 是最基本的通用编码。

## 基本规则 {#csv-rule}

- CSV 是一种被分隔的数据格式，它有被逗号字符分隔的字段/列和以换行结束的记录/行。

- CSV 文件不要求特定的字符编码、字节序或行结束符格式（某些软件不支持所有行结束变体）。

- 一条记录结束于行结束符。然而，行结束符可能被作为数据嵌入到字段中，所以软件必须识别被包裹的行结束符（见下述），以便从可能的多行中正确组装一条完整的记录。

- 所有记录应当有相同数目、相同顺序的字段。
- 字段中的数据被翻译为一系列字符，而不是一系列比特或字节（见 RFC 2046，section 4.1）。例如，数值量 65535 可以被表现为 5 个 ASCII 字符“65535”（或其它形式如“0xFFFF”、“000065535.000E+00”等等）；但不会被作为单个二进制整数的 2 字节序列（而非两个字符）来处理。如果不遵循这个“纯文本”的惯例，那么该 CSV 文件就不能包含足够的信息来正确地翻译它，该 CSV 文件将不大可能在不同的电脑架构间正确传递，并且将不能与 text/csv MIME 类型保持一致。

- 相邻字段必须被单个逗号分隔开。然而，“CSV”格式在分隔字符的选择上变化很大。特别是在某些区域设置中逗号被用作小数点，则会使用分号、制表符或其它字符来代替。

```
1997,Ford,E350
```

- 任何字段都可以被包裹（使用双引号字符）。某些字段必须被包裹，详见后续规则。

```
"1997","Ford","E350"
```

- 如果字段包含被嵌入的逗号，必须被包裹。

```
1997,Ford,E350,"Super, luxurious truck"
```

- 每个被嵌入的双引号字符必须被表示为两个双引号字符。

```
1997,Ford,E350,"Super, ""luxurious"" truck"
```

- 如果字段包含被嵌入的换行，必须被包裹（然而，许多简单的 CSV 实现不支持字段内换行）。

```
1997,Ford,E350,"Go get one now
they are going fast"
```

- 在某些 CSV 实现中，起头和结尾的空格和制表符被截掉。这一实践是有争议的，也不符合 RFC 4180。RFC 4180 声明“空格被看作字段的一部分，不应当被忽略。”。

```
1997, Ford, E350
not same as
1997,Ford,E350
```

- 然而，该 RFC 并没有说当空白字符出现在被包裹的值之外该如何处理。

```
1997, "Ford" ,E350
```

- 在截掉起头和结尾空格的 CSV 实现中，将这种空格视为有意义数据的字段必须被包裹。

```
1997,Ford,E350," Super luxurious truck "
```

- 第一条记录可以是“表头”，它在每个字段中包含列名（并没有可靠途径来告知一个文件是否这样包含表头；然而，一般在列名中仅使用字母、数字和下划线，而不使用其它字符）。

```
Year,Make,Model
1997,Ford,E350
2000,Mercury,Cougar
```

## 举例

| 年份 | 品牌  | 型号                                   | 描述                                 | 价格    |
| ---- | ----- | -------------------------------------- | ------------------------------------ | ------- |
| 1997 | Ford  | E350                                   | ac, abs, moon                        | 3000.00 |
| 1999 | Chevy | Venture "Extended Edition"             |                                      | 4900.00 |
| 1999 | Chevy | Venture "Extended Edition, Very Large" |                                      | 5000.00 |
| 1996 | Jeep  | Grand Cherokee                         | MUST SELL!<br>air, moon roof, loaded | 4799.00 |

以上数据表可以以 CSV 格式表示如下：

```
Year,Make,Model,Description,Price
1997,Ford,E350,"ac, abs, moon",3000.00
1999,Chevy,"Venture ""Extended Edition""","",4900.00
1999,Chevy,"Venture ""Extended Edition, Very Large""",,5000.00
1996,Jeep,Grand Cherokee,"MUST SELL!
air, moon roof, loaded",4799.00
```