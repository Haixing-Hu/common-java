## 提升此项目的单元测试覆盖率

### lang 模块
- [x] Argument
  > 已完成测试（47个测试用例），但建议增加以下测试以提高覆盖率：
  > - 浮点数比较的边界情况测试（epsilon参数）
  > - 对象类型的requireInEnum测试
  > - 更多的范围检查边界情况
  > - javadoc注释已补全
- [x] ArrayUtils
  > 已完成测试，测试内容全面，涵盖了所有原始类型和引用类型数组的各种操作，包括：nullToEmpty、toString、hashCode、subarray、sameLength、reverse、indexOf/lastIndexOf/contains、类型转换、isEmpty、add/remove、sum/product、nullIfEmpty、lowerBound/upperBound等方法
- [ ] Assignable
- [ ] Assignment
- [x] BigDecimalUtils
  > 已完成测试（48个测试用例），全面覆盖了所有类型转换方法（toBoolean/toByte/toChar/toShort/toInt/toLong/toFloat/toDouble/toString/toDate/toByteArray/toClass/toBigInteger等），以及辅助方法（equals/limitPrecision/normalizeMoney/formatMoney/isComparable）
- [x] BigIntegerUtils
  > 已完成测试（43个测试用例），全面覆盖了所有类型转换方法（toBoolean/toByte/toChar/toShort/toInt/toLong/toFloat/toDouble/toString/toDate/toByteArray/toClass/toBigDecimal等）以及isComparable方法
- [ ] BooleanUtils
- [ ] ByteArrayUtils
- [ ] ByteUtils
- [ ] CharSequenceUtils
- [ ] CharUtils
- [ ] Choose
- [ ] ClassKey
- [ ] ClassUtils
- [ ] Clearable
- [ ] CloneableEx
- [ ] CompareToBuilder
- [ ] Comparison
- [ ] DateUtils
- [ ] DecoratingClassLoader
- [ ] DesktopApi
- [ ] DoubleUtils
- [ ] EnumUtils
- [ ] Equality
- [ ] FloatUtils
- [ ] Hash
- [ ] Hash64
- [ ] InstantUtils
- [ ] IntUtils
- [ ] LocalDateTimeUtils
- [ ] LocalDateUtils
- [ ] LocalTimeUtils
- [ ] LocalizedEnum
- [ ] LongUtils
- [ ] NumericUtils
- [ ] ObjectUtils
- [ ] OverridingClassLoader
- [ ] Selection
- [ ] ShortUtils
- [ ] Size
- [ ] SplitOption
- [ ] StringUtils
- [ ] Swappable
- [ ] SystemUtils
- [ ] ThrowableUtils
- [ ] Type
- [ ] TypeUtils
- [ ] TypeXmlAdapter

### util 模块
- [ ] CommandExecutor
- [ ] ComparisonOperator
- [ ] CompressionTools
- [ ] CurrentTimeMillis
- [ ] DataType
- [ ] DatePattern
- [ ] DatePatternBinarySerializer
- [ ] DatePatternXmlSerializer
- [ ] HumanReadable
- [ ] LogicRelation
- [ ] MapUtils
- [ ] Nullability
- [ ] RegionUnit
- [ ] Result
- [ ] UuidUtils
- [ ] Version
- [ ] VersionBinarySerializer
- [ ] VersionMismatchException
- [ ] VersionSignature
- [ ] VersionXmlSerializer

#### util.buffer 子模块
- [ ] BooleanBuffer
- [ ] Buffer
- [ ] ByteBuffer
- [ ] CharBuffer
- [ ] DoubleBuffer
- [ ] FloatBuffer
- [ ] IntBuffer
- [ ] LongBuffer
- [ ] ShortBuffer

#### util.cache 子模块
- [ ] AbstractCache
- [ ] Cache
- [ ] SynchronizedCache

#### util.clock 子模块
- [ ] MockClock
- [ ] TimeMeter

#### util.codec 子模块
- [ ] AlphabetBooleanCodec
- [ ] Base64Codec
- [ ] Base64Utils
- [ ] BigDecimalCodec
- [ ] BigIntegerCodec
- [ ] BooleanCodec
- [ ] CharsetCodec
- [ ] Codec
- [ ] CompactDateCodec
- [ ] CompactDateTimeCodec
- [ ] ConfigurableEnumCodec
- [ ] DateCodec
- [ ] Decoder
- [ ] DecoderFactory
- [ ] DoubleCodec
- [ ] DurationCodec
- [ ] Encoder
- [ ] EnumCodec
- [ ] EnumListCodec
- [ ] EnumMap
- [ ] EnumMapper
- [ ] FloatCodec
- [ ] HexCodec
- [ ] InstantCodec
- [ ] IntegralBooleanCodec
- [ ] JsonObjectCodec
- [ ] LocalDateCodec
- [ ] LocalDateTimeCodec
- [ ] LocalTimeCodec
- [ ] LongCodec
- [ ] MoneyCodec
- [ ] MonthDayCodec
- [ ] ObjectCodec
- [ ] OffsetTimeCodec
- [ ] PeriodCodec
- [ ] PosixLocaleCodec
- [ ] RawEnumCodec
- [ ] StandardBooleanCodec
- [ ] StringArrayCodec
- [ ] StringListCodec
- [ ] TimestampCodec
- [ ] UrlEncodedFormCodec
- [ ] UrlEncodingCodec
- [ ] YearCodec
- [ ] YearMonthCodec
- [ ] ZoneIdCodec
- [ ] ZoneOffsetCodec

#### util.compare 子模块
- [ ] ComparableComparator
- [ ] CompareListByElementAtIndex
- [ ] ComparePairByFirstPart
- [ ] ComparePairBySecondPart
- [ ] EqualityTester
- [ ] LocalDateComparator
- [ ] StringComparator

#### util.expand 子模块
- [ ] DoubleExpansionPolicy
- [ ] ExpansionPolicy
- [ ] JustFitExpansionPolicy
- [ ] MemorySavingExpansionPolicy

#### util.filter 子模块
- [ ] AbstractFilter
- [ ] ChainedFilter
- [ ] Filter
- [ ] FilterAction
- [ ] FilterState
- [ ] FilterUtils

#### util.generator 子模块
- [ ] CodeGenerator
- [ ] DateSequenceCodeGenerator
- [ ] DefaultUsernameGenerator
- [ ] MockRandomNumberTokenGenerator
- [ ] PasswordGenerator
- [ ] PhoneNumberPasswordGenerator
- [ ] RandomNumberTokenGenerator
- [ ] RandomPasswordGenerator
- [ ] TimestampCodeGenerator
- [ ] TokenGenerator
- [ ] UsernameGenerator
- [ ] UuidRandomTokenGenerator

#### util.mapper 子模块
- [ ] BasicRowMapper
- [ ] ColumnHeaderTransformer
- [ ] RowMapper
- [ ] RowMapperBuilder

#### util.pair 子模块
- [ ] BooleanPair
- [ ] BytePair
- [ ] CharPair
- [ ] DoublePair
- [ ] FloatPair
- [ ] IntPair
- [ ] KeyValuePair
- [ ] KeyValuePairList
- [ ] KeyValuePairListBuilder
- [ ] LongPair
- [ ] NameValuePair
- [ ] Pair
- [ ] ShortPair

#### util.properties 子模块
- [ ] DefaultPropertiesPersister
- [ ] PropertiesPersister
- [ ] PropertiesUtils

#### util.range 子模块
- [ ] CloseRange
- [ ] DateRange
- [ ] InstantRange
- [ ] IntRange
- [ ] LocalDateRange
- [ ] LocalDateTimeRange
- [ ] LocalTimeRange
- [ ] LocalTimeRangeList
- [ ] LongRange
- [ ] MoneyRange
- [ ] UnmodifiableCloseRange

#### util.ref 子模块
- [ ] BooleanArrayRef
- [ ] BooleanRef
- [ ] ByteArrayRef
- [ ] ByteRef
- [ ] CharArrayRef
- [ ] CharRef
- [ ] DoubleArrayRef
- [ ] DoubleRef
- [ ] FloatArrayRef
- [ ] FloatRef
- [ ] IntArrayRef
- [ ] IntRef
- [ ] LongArrayRef
- [ ] LongRef
- [ ] ShortArrayRef
- [ ] ShortRef
- [ ] StringArrayRef
- [ ] StringRef

#### util.retry 子模块
- [ ] DefaultRetryOptions
- [ ] RetryBuilder
- [ ] RetryOptions

#### util.selection 子模块
- [ ] RandomSelectionStrategy
- [ ] RoundRobinSelectionStrategy
- [ ] SelectionStrategy

#### util.statemachine 子模块
- [ ] StateMachine
- [ ] Transition

#### util.transformer 子模块
- [ ] AbstractTransformer
- [ ] ChainedTransformer
- [ ] CharSequenceTransformer
- [ ] ListTransformer
- [ ] TransformUtils
- [ ] Transformer

#### util.triple 子模块
- [ ] BooleanTriple
- [ ] ByteTriple
- [ ] CharTriple
- [ ] DoubleTriple
- [ ] FloatTriple
- [ ] IntTriple
- [ ] LongTriple
- [ ] ShortTriple
- [ ] Triple

#### util.value 子模块
- [ ] BasicMultiValues
- [ ] BasicNamedMultiValues
- [ ] BasicNamedValue
- [ ] BasicValue
- [ ] MultiValues
- [ ] NamedMultiValues
- [ ] NamedValue
- [ ] Value

### reflect 模块
- [ ] AccessibleUtils
- [ ] AnnotationUtils
- [ ] BeanInfo
- [ ] ConstructorUtils
- [ ] FieldInfo
- [ ] FieldSignature
- [ ] FieldUtils
- [ ] MemberInfo
- [ ] MemberUtils
- [ ] MethodInfo
- [ ] MethodSignature
- [ ] MethodUtils
- [ ] ObjectGraphUtils
- [ ] Property
- [ ] PropertyUtils
- [ ] TypeInfo

### concurrent 模块
- [ ] ConcurrencyUtils
- [ ] Parallel
- [ ] TaskUtils
- [ ] ThreadUtils

### io 模块
- [ ] ChecksumInputStream
- [ ] ChecksumOutputStream
- [ ] Endian
- [ ] FileUtils
- [ ] FilenameUtils
- [ ] InputStreamSource
- [ ] InputUtils
- [ ] IoUtils
- [ ] LogUtils
- [ ] LoggerPrinter
- [ ] MapRecordReader
- [ ] MapRecordWriter
- [ ] MultiplePrinter
- [ ] NioFileInputStream
- [ ] NioFileOutputStream
- [ ] NullOutputStream
- [ ] NullWriter
- [ ] OpenOption
- [ ] Openable
- [ ] OperationOption
- [ ] OutputUtils
- [ ] PrintStreamPrinter
- [ ] Printer
- [ ] RamFile
- [ ] ReadLimitInputStream
- [ ] RecordReader
- [ ] RecordWriter
- [ ] Seekable
- [ ] SeekableFileInputStream
- [ ] SeekableFileOutputStream
- [ ] SeekableInputStream
- [ ] SeekableOutputStream
- [ ] Serializer
- [ ] VfsUtils

#### io.resource 子模块
- [ ] AbstractFileResolvingResource
- [ ] AbstractResource
- [ ] ByteArrayResource
- [ ] ClassPathResource
- [ ] ClassRelativeResourceLoader
- [ ] ContextResource
- [ ] DefaultResourceLoader
- [ ] DescriptiveResource
- [ ] FileSystemResource
- [ ] FileSystemResourceLoader
- [ ] FileUrlResource
- [ ] InputStreamResource
- [ ] ModuleResource
- [ ] PathResource
- [ ] ProtocolResolver
- [ ] Resource
- [ ] ResourceLoader
- [ ] ResourceLoaderAware
- [ ] ResourceUtils
- [ ] UrlResource
- [ ] VfsResource
- [ ] WritableResource

#### io.serialize 子模块
- [ ] BinarySerialization
- [ ] BinarySerializer
- [ ] XmlSerialization
- [ ] XmlSerializer

### math 模块
- [ ] ByteBit
- [ ] IntBit
- [ ] LongBit
- [ ] MathEx
- [ ] RandomEx
- [ ] ShortBit

### text 模块
- [ ] Ascii
- [ ] AsciiStringUtils
- [ ] AsciiTrie
- [ ] BooleanFormat
- [ ] CStringLiteral
- [ ] CaseFormat
- [ ] CharArrayWrapper
- [ ] CharSequenceCodePointIterator
- [ ] CharsetUtils
- [ ] CodePointIterator
- [ ] DateFormat
- [ ] EscapeUtils
- [ ] FormatFlag
- [ ] FormatFlags
- [ ] FormatUtils
- [ ] Formatter
- [ ] Glob
- [ ] Inflector
- [ ] Joiner
- [ ] NamingStyleUtils
- [ ] NumberFormat
- [ ] ParseOptions
- [ ] ParseUtils
- [ ] Parser
- [ ] Pattern
- [ ] PatternMap
- [ ] PatternType
- [ ] Remover
- [ ] Replacer
- [ ] Searcher
- [ ] Splitter
- [ ] SqlLikePattern
- [ ] Stripper
- [ ] TokenParser
- [ ] Trie
- [ ] TrieMap
- [ ] Unicode
- [ ] Utf16
- [ ] Utf8
- [ ] WinCodePage

#### text.html 子模块
- [ ] HtmlAttribute
- [ ] HtmlAttributeValue
- [ ] HtmlEvent
- [ ] HtmlTag
- [ ] HyperLink
- [ ] HyperLinkSet

#### text.tostring 子模块
- [ ] DefaultToStringStyle
- [ ] MultiLineToStringStyle
- [ ] NoFieldNameToStringStyle
- [ ] ShortPrefixToStringStyle
- [ ] SimpleToStringStyle
- [ ] StandardToStringStyle
- [ ] ToStringBuilder
- [ ] ToStringStyle

#### text.translate 子模块
- [ ] AggregateTranslator
- [ ] CharSequenceTranslator
- [ ] CodePointTranslator
- [ ] CsvEscaper
- [ ] CsvUnescaper
- [ ] EntityArrays
- [ ] JavaUnicodeEscaper
- [ ] LookupTranslator
- [ ] NumericEntityEscaper
- [ ] NumericEntityUnescaper
- [ ] OctalUnescaper
- [ ] SinglePassTranslator
- [ ] UnicodeEscaper
- [ ] UnicodeUnescaper
- [ ] UnicodeUnpairedSurrogateRemover
- [ ] XsiUnescaper

#### text.xml 子模块
- [ ] DomElementIterator
- [ ] DomNodeIterator
- [ ] DomUtils
- [ ] NodePattern
- [ ] TagPattern
- [ ] XmlEscapeWriter
- [ ] XmlEscapingWriterFactory
- [ ] XmlUtils

### config 模块
- [ ] CommonsConfig
- [ ] Config
- [ ] ConfigUtils
- [ ] Configurable
- [ ] DefaultConfig
- [ ] DefaultProperty
- [ ] Property
- [ ] PropertiesConfig
- [ ] WritableConfig
- [ ] WritableConfigurable

### error 模块
- [ ] ConfigurationError
- [ ] ErrorCode
- [ ] ErrorInfo
- [ ] ErrorInfoConvertable
- [ ] ExceptionUtils
- [ ] InitializationError
- [ ] InvalidJsonFormatException
- [ ] InvalidPropertyValueError
- [ ] InvalidSettingValueException
- [ ] NotConfiguredError
- [ ] NotImplementedError
- [ ] NullArgumentException
- [ ] PropertyHasNoValueError
- [ ] PropertyIsListError
- [ ] PropertyNotExistError
- [ ] PropertyTypeMismatchError
- [ ] TypeConvertException
- [ ] TypeMismatchException
- [ ] UnexpectedError
- [ ] UnsupportedAlgorithmException
- [ ] UnsupportedByteOrderException
- [ ] UnsupportedContentTypeException
- [ ] UnsupportedDataTypeException
- [ ] XmlConfigurationError

### net 模块
- [ ] ApiBuilder
- [ ] DefaultHttpClientOptions
- [ ] DefaultPorts
- [ ] DomainSuffix
- [ ] DomainSuffixRegistry
- [ ] ErrorResponseInterceptor
- [ ] Host
- [ ] HostBinarySerializer
- [ ] HostXmlSerializer
- [ ] HttpClientBuilder
- [ ] HttpClientOptions
- [ ] HttpMethod
- [ ] InetAddressUtils
- [ ] NetworkUtils
- [ ] RandomUserAgent
- [ ] Redirection
- [ ] ResponseBodyJsonConverter
- [ ] TopLevelDomain
- [ ] UriBuilder
- [ ] Url
- [ ] UrlBinarySerializer
- [ ] UrlEncodingUtils
- [ ] UrlPart
- [ ] UrlPattern
- [ ] UrlPatternBinarySerializer
- [ ] UrlPatternMap
- [ ] UrlUtils
- [ ] UrlXmlSerializer

#### net.interceptor 子模块
- [ ] AddHeaderInterceptor
- [ ] AddHeadersInterceptor
- [ ] AuthorizationInterceptor
- [ ] ConnectionLoggingEventListener
- [ ] HttpLoggingInterceptor

### security 模块
- [ ] AsymmetricCryptoAlgorithm
- [ ] AsymmetricCryptoKeyPairGenerator
- [ ] Base64SecretKeyCodec
- [ ] CryptoAlgorithm
- [ ] CryptoConfig
- [ ] CryptoMode
- [ ] CryptoPadding
- [ ] Decryptor
- [ ] DigestAlgorithm
- [ ] Digester
- [ ] Encryptor
- [ ] KeyAlgorithm
- [ ] KeyDecoder
- [ ] KeyFormat
- [ ] KeyGeneratorAlgorithm
- [ ] MacAlgorithm
- [ ] MacDigester
- [ ] MacSecretKeyCodec
- [ ] PasswordBasedKeyDecoder
- [ ] Pkcs8PrivateKeyCodec
- [ ] PrivateKeyCodec
- [ ] PrivateKeyDecoder
- [ ] PrivateKeyEncoder
- [ ] PublicKeyCodec
- [ ] PublicKeyDecoder
- [ ] PublicKeyEncoder
- [ ] RawSecretKeyCodec
- [ ] SecretKeyCodec
- [ ] SecretKeyDecoder
- [ ] SecretKeyEncoder
- [ ] SecureRandomAlgorithm
- [ ] SignRequest
- [ ] SignatureAlgorithm
- [ ] SignatureKeyPairGenerator
- [ ] SignaturePrivateKeyCodec
- [ ] SignaturePublicKeyCodec
- [ ] SignatureSigner
- [ ] SignatureVerifier
- [ ] SignedMessage
- [ ] SshPrivateKeyCodec
- [ ] SshPublicKeyCodec
- [ ] SslContextAlgorithm
- [ ] SymmetricCryptoAlgorithm
- [ ] X509PublicKeyCodec

### datastructure 模块
- [ ] ConcurrentHashSet
- [ ] HashMapTree
- [ ] LinkedListMap
- [ ] LinkedListTree
- [ ] StringList
- [ ] Tree
- [ ] TreeMapTree

#### datastructure.primitive 子模块
- [ ] DoubleArrayList
- [ ] DoubleCollection
- [ ] DoubleIterator
- [ ] DoubleList
- [ ] DoubleListIterator
- [ ] DoubleStack
- [ ] FloatArrayList
- [ ] FloatCollection
- [ ] FloatIterator
- [ ] FloatList
- [ ] FloatListIterator
- [ ] FloatStack
- [ ] IntArrayList
- [ ] IntCollection
- [ ] IntIterator
- [ ] IntList
- [ ] IntListIterator
- [ ] IntStack
- [ ] LongArrayList
- [ ] LongCollection
- [ ] LongIterator
- [ ] LongList
- [ ] LongListIterator
- [ ] LongStack
- [ ] RandomAccessBooleanList
- [ ] RandomAccessByteList
- [ ] RandomAccessCharList
- [ ] RandomAccessDoubleList
- [ ] RandomAccessFloatList
- [ ] RandomAccessIntList
- [ ] RandomAccessLongList
- [ ] RandomAccessShortList
- [ ] ShortArrayList
- [ ] ShortCollection
- [ ] ShortIterator
- [ ] ShortList
- [ ] ShortListIterator
- [ ] ShortStack
- [ ] UnmodifiableBooleanCollection
- [ ] UnmodifiableBooleanIterator
- [ ] UnmodifiableBooleanList
- [ ] UnmodifiableBooleanListIterator
- [ ] UnmodifiableByteCollection
- [ ] UnmodifiableByteIterator
- [ ] UnmodifiableByteList
- [ ] UnmodifiableByteListIterator
- [ ] UnmodifiableCharCollection
- [ ] UnmodifiableCharIterator
- [ ] UnmodifiableCharList
- [ ] UnmodifiableCharListIterator
- [ ] UnmodifiableDoubleCollection
- [ ] UnmodifiableDoubleIterator
- [ ] UnmodifiableDoubleList
- [ ] UnmodifiableDoubleListIterator
- [ ] UnmodifiableFloatCollection
- [ ] UnmodifiableFloatIterator
- [ ] UnmodifiableFloatList
- [ ] UnmodifiableFloatListIterator
- [ ] UnmodifiableIntCollection
- [ ] UnmodifiableIntIterator
- [ ] UnmodifiableIntList
- [ ] UnmodifiableIntListIterator
- [ ] UnmodifiableLongCollection
- [ ] UnmodifiableLongIterator
- [ ] UnmodifiableLongList
- [ ] UnmodifiableLongListIterator
- [ ] UnmodifiableShortCollection
- [ ] UnmodifiableShortIterator
- [ ] UnmodifiableShortList
- [ ] UnmodifiableShortListIterator

### i18n 模块
- [ ] DefaultMessageSourceResolvable
- [ ] HierarchicalMessageSource
- [ ] Localized
- [ ] LocaleContext
- [ ] LocaleContextHolder
- [ ] LocaleUtils
- [ ] MessageSource
- [ ] MessageSourceAccessor
- [ ] MessageSourceResolvable
- [ ] MessageSourceResourceBundle
- [ ] MessageSourceSupport
- [ ] NamedInheritableThreadLocal
- [ ] NamedThreadLocal
- [ ] NoSuchMessageException
- [ ] ReloadableResourceBundleMessageSource
- [ ] ResourceBundleMessageSource
- [ ] ResourceBundleUtils
- [ ] SimpleLocaleContext
- [ ] SimpleTimeZoneAwareLocaleContext
- [ ] TimeZoneAwareLocaleContext
- [ ] Utf8Control

### image 模块
- [ ] ImageSize

### sql 模块
- [ ] ClientInfo
- [ ] ColumnInfo
- [ ] ComposedCriterion
- [ ] ComposedCriterionBuilder
- [ ] Criterion
- [ ] CriterionBuilder
- [ ] DaoOperation
- [ ] JdbcOperation
- [ ] JdbcTemplate
- [ ] JdbcUtils
- [ ] NullSortOption
- [ ] Page
- [ ] PageRequest
- [ ] ParamInfo
- [ ] PreparedStatementSetter
- [ ] QueryOperation
- [ ] QueryParams
- [ ] RowMapper
- [ ] RowProcessor
- [ ] SimpleCriterion
- [ ] Sort
- [ ] SortBuilder
- [ ] SortOrder
- [ ] SortRequest
- [ ] StandardTypeMapping
- [ ] TypeInfo
- [ ] WithPageRequestParams
- [ ] WithSortRequestParams
