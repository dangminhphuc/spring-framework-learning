# Type Conversion

## Converter<S, T> interface

To create your own converter, implement the `Converter` interface and parameterize `S` as the type you are converting
from
and `T` as the type you are converting to.

[NumberToMonthConverter.java](src%2Fmain%2Fjava%2Fcom%2Fdangminhphuc%2Fdev%2Ftypeconversion%2FNumberToMonthConverter.java)

## ConverterFactory<S, T>

`ConversionFailedException` is a runtime exception thrown by Spring when a type conversion fails — typically due to
invalid input or an unsupported target type — and it wraps the original exception to provide detailed context about the
failure.

## Use case

1 source → 1 target Converter<S, T>
1 source → nhiều target (cùng nhóm logic)    ConverterFactory<S, T>
Nhiều source + nhiều target (tùy chỉnh sâu)    GenericConverter