@file:OptIn(ExperimentalJsStatic::class)

package ir.alisalimik.kotstone.internal

import kotlin.js.ExperimentalJsStatic
import kotlin.js.JsStatic

@JsStatic internal const val WASM_INS_UNREACHABLE = 0

@JsStatic internal const val WASM_INS_NOP = 1

@JsStatic internal const val WASM_INS_BLOCK = 2

@JsStatic internal const val WASM_INS_LOOP = 3

@JsStatic internal const val WASM_INS_IF = 4

@JsStatic internal const val WASM_INS_ELSE = 5

@JsStatic internal const val WASM_INS_END = 11

@JsStatic internal const val WASM_INS_BR = 12

@JsStatic internal const val WASM_INS_BR_IF = 13

@JsStatic internal const val WASM_INS_BR_TABLE = 14

@JsStatic internal const val WASM_INS_RETURN = 15

@JsStatic internal const val WASM_INS_CALL = 16

@JsStatic internal const val WASM_INS_CALL_INDIRECT = 17

@JsStatic internal const val WASM_INS_DROP = 26

@JsStatic internal const val WASM_INS_SELECT = 27

@JsStatic internal const val WASM_INS_GET_LOCAL = 32

@JsStatic internal const val WASM_INS_SET_LOCAL = 33

@JsStatic internal const val WASM_INS_TEE_LOCAL = 34

@JsStatic internal const val WASM_INS_GET_GLOBAL = 35

@JsStatic internal const val WASM_INS_SET_GLOBAL = 36

@JsStatic internal const val WASM_INS_I32_LOAD = 40

@JsStatic internal const val WASM_INS_I64_LOAD = 41

@JsStatic internal const val WASM_INS_F32_LOAD = 42

@JsStatic internal const val WASM_INS_F64_LOAD = 43

@JsStatic internal const val WASM_INS_I32_LOAD8_S = 44

@JsStatic internal const val WASM_INS_I32_LOAD8_U = 45

@JsStatic internal const val WASM_INS_I32_LOAD16_S = 46

@JsStatic internal const val WASM_INS_I32_LOAD16_U = 47

@JsStatic internal const val WASM_INS_I64_LOAD8_S = 48

@JsStatic internal const val WASM_INS_I64_LOAD8_U = 49

@JsStatic internal const val WASM_INS_I64_LOAD16_S = 50

@JsStatic internal const val WASM_INS_I64_LOAD16_U = 51

@JsStatic internal const val WASM_INS_I64_LOAD32_S = 52

@JsStatic internal const val WASM_INS_I64_LOAD32_U = 53

@JsStatic internal const val WASM_INS_I32_STORE = 54

@JsStatic internal const val WASM_INS_I64_STORE = 55

@JsStatic internal const val WASM_INS_F32_STORE = 56

@JsStatic internal const val WASM_INS_F64_STORE = 57

@JsStatic internal const val WASM_INS_I32_STORE8 = 58

@JsStatic internal const val WASM_INS_I32_STORE16 = 59

@JsStatic internal const val WASM_INS_I64_STORE8 = 60

@JsStatic internal const val WASM_INS_I64_STORE16 = 61

@JsStatic internal const val WASM_INS_I64_STORE32 = 62

@JsStatic internal const val WASM_INS_CURRENT_MEMORY = 63

@JsStatic internal const val WASM_INS_GROW_MEMORY = 64

@JsStatic internal const val WASM_INS_I32_CONST = 65

@JsStatic internal const val WASM_INS_I64_CONST = 66

@JsStatic internal const val WASM_INS_F32_CONST = 67

@JsStatic internal const val WASM_INS_F64_CONST = 68

@JsStatic internal const val WASM_INS_I32_EQZ = 69

@JsStatic internal const val WASM_INS_I32_EQ = 70

@JsStatic internal const val WASM_INS_I32_NE = 71

@JsStatic internal const val WASM_INS_I32_LT_S = 72

@JsStatic internal const val WASM_INS_I32_LT_U = 73

@JsStatic internal const val WASM_INS_I32_GT_S = 74

@JsStatic internal const val WASM_INS_I32_GT_U = 75

@JsStatic internal const val WASM_INS_I32_LE_S = 76

@JsStatic internal const val WASM_INS_I32_LE_U = 77

@JsStatic internal const val WASM_INS_I32_GE_S = 78

@JsStatic internal const val WASM_INS_I32_GE_U = 79

@JsStatic internal const val WASM_INS_I64_EQZ = 80

@JsStatic internal const val WASM_INS_I64_EQ = 81

@JsStatic internal const val WASM_INS_I64_NE = 82

@JsStatic internal const val WASM_INS_I64_LT_S = 83

@JsStatic internal const val WASM_INS_I64_LT_U = 84

@JsStatic internal const val WASM_INS_I64_GT_S = 85

@JsStatic internal const val WASM_INS_I64_GT_U = 86

@JsStatic internal const val WASM_INS_I64_LE_S = 87

@JsStatic internal const val WASM_INS_I64_LE_U = 88

@JsStatic internal const val WASM_INS_I64_GE_S = 89

@JsStatic internal const val WASM_INS_I64_GE_U = 90

@JsStatic internal const val WASM_INS_F32_EQ = 91

@JsStatic internal const val WASM_INS_F32_NE = 92

@JsStatic internal const val WASM_INS_F32_LT = 93

@JsStatic internal const val WASM_INS_F32_GT = 94

@JsStatic internal const val WASM_INS_F32_LE = 95

@JsStatic internal const val WASM_INS_F32_GE = 96

@JsStatic internal const val WASM_INS_F64_EQ = 97

@JsStatic internal const val WASM_INS_F64_NE = 98

@JsStatic internal const val WASM_INS_F64_LT = 99

@JsStatic internal const val WASM_INS_F64_GT = 100

@JsStatic internal const val WASM_INS_F64_LE = 101

@JsStatic internal const val WASM_INS_F64_GE = 102

@JsStatic internal const val WASM_INS_I32_CLZ = 103

@JsStatic internal const val WASM_INS_I32_CTZ = 104

@JsStatic internal const val WASM_INS_I32_POPCNT = 105

@JsStatic internal const val WASM_INS_I32_ADD = 106

@JsStatic internal const val WASM_INS_I32_SUB = 107

@JsStatic internal const val WASM_INS_I32_MUL = 108

@JsStatic internal const val WASM_INS_I32_DIV_S = 109

@JsStatic internal const val WASM_INS_I32_DIV_U = 110

@JsStatic internal const val WASM_INS_I32_REM_S = 111

@JsStatic internal const val WASM_INS_I32_REM_U = 112

@JsStatic internal const val WASM_INS_I32_AND = 113

@JsStatic internal const val WASM_INS_I32_OR = 114

@JsStatic internal const val WASM_INS_I32_XOR = 115

@JsStatic internal const val WASM_INS_I32_SHL = 116

@JsStatic internal const val WASM_INS_I32_SHR_S = 117

@JsStatic internal const val WASM_INS_I32_SHR_U = 118

@JsStatic internal const val WASM_INS_I32_ROTL = 119

@JsStatic internal const val WASM_INS_I32_ROTR = 120

@JsStatic internal const val WASM_INS_I64_CLZ = 121

@JsStatic internal const val WASM_INS_I64_CTZ = 122

@JsStatic internal const val WASM_INS_I64_POPCNT = 123

@JsStatic internal const val WASM_INS_I64_ADD = 124

@JsStatic internal const val WASM_INS_I64_SUB = 125

@JsStatic internal const val WASM_INS_I64_MUL = 126

@JsStatic internal const val WASM_INS_I64_DIV_S = 127

@JsStatic internal const val WASM_INS_I64_DIV_U = 128

@JsStatic internal const val WASM_INS_I64_REM_S = 129

@JsStatic internal const val WASM_INS_I64_REM_U = 130

@JsStatic internal const val WASM_INS_I64_AND = 131

@JsStatic internal const val WASM_INS_I64_OR = 132

@JsStatic internal const val WASM_INS_I64_XOR = 133

@JsStatic internal const val WASM_INS_I64_SHL = 134

@JsStatic internal const val WASM_INS_I64_SHR_S = 135

@JsStatic internal const val WASM_INS_I64_SHR_U = 136

@JsStatic internal const val WASM_INS_I64_ROTL = 137

@JsStatic internal const val WASM_INS_I64_ROTR = 138

@JsStatic internal const val WASM_INS_F32_ABS = 139

@JsStatic internal const val WASM_INS_F32_NEG = 140

@JsStatic internal const val WASM_INS_F32_CEIL = 141

@JsStatic internal const val WASM_INS_F32_FLOOR = 142

@JsStatic internal const val WASM_INS_F32_TRUNC = 143

@JsStatic internal const val WASM_INS_F32_NEAREST = 144

@JsStatic internal const val WASM_INS_F32_SQRT = 145

@JsStatic internal const val WASM_INS_F32_ADD = 146

@JsStatic internal const val WASM_INS_F32_SUB = 147

@JsStatic internal const val WASM_INS_F32_MUL = 148

@JsStatic internal const val WASM_INS_F32_DIV = 149

@JsStatic internal const val WASM_INS_F32_MIN = 150

@JsStatic internal const val WASM_INS_F32_MAX = 151

@JsStatic internal const val WASM_INS_F32_COPYSIGN = 152

@JsStatic internal const val WASM_INS_F64_ABS = 153

@JsStatic internal const val WASM_INS_F64_NEG = 154

@JsStatic internal const val WASM_INS_F64_CEIL = 155

@JsStatic internal const val WASM_INS_F64_FLOOR = 156

@JsStatic internal const val WASM_INS_F64_TRUNC = 157

@JsStatic internal const val WASM_INS_F64_NEAREST = 158

@JsStatic internal const val WASM_INS_F64_SQRT = 159

@JsStatic internal const val WASM_INS_F64_ADD = 160

@JsStatic internal const val WASM_INS_F64_SUB = 161

@JsStatic internal const val WASM_INS_F64_MUL = 162

@JsStatic internal const val WASM_INS_F64_DIV = 163

@JsStatic internal const val WASM_INS_F64_MIN = 164

@JsStatic internal const val WASM_INS_F64_MAX = 165

@JsStatic internal const val WASM_INS_F64_COPYSIGN = 166

@JsStatic internal const val WASM_INS_I32_WARP_I64 = 167

@JsStatic internal const val WASM_INS_I32_TRUNC_S_F32 = 168

@JsStatic internal const val WASM_INS_I32_TRUNC_U_F32 = 169

@JsStatic internal const val WASM_INS_I32_TRUNC_S_F64 = 170

@JsStatic internal const val WASM_INS_I32_TRUNC_U_F64 = 171

@JsStatic internal const val WASM_INS_I64_EXTEND_S_I32 = 172

@JsStatic internal const val WASM_INS_I64_EXTEND_U_I32 = 173

@JsStatic internal const val WASM_INS_I64_TRUNC_S_F32 = 174

@JsStatic internal const val WASM_INS_I64_TRUNC_U_F32 = 175

@JsStatic internal const val WASM_INS_I64_TRUNC_S_F64 = 176

@JsStatic internal const val WASM_INS_I64_TRUNC_U_F64 = 177

@JsStatic internal const val WASM_INS_F32_CONVERT_S_I32 = 178

@JsStatic internal const val WASM_INS_F32_CONVERT_U_I32 = 179

@JsStatic internal const val WASM_INS_F32_CONVERT_S_I64 = 180

@JsStatic internal const val WASM_INS_F32_CONVERT_U_I64 = 181

@JsStatic internal const val WASM_INS_F32_DEMOTE_F64 = 182

@JsStatic internal const val WASM_INS_F64_CONVERT_S_I32 = 183

@JsStatic internal const val WASM_INS_F64_CONVERT_U_I32 = 184

@JsStatic internal const val WASM_INS_F64_CONVERT_S_I64 = 185

@JsStatic internal const val WASM_INS_F64_CONVERT_U_I64 = 186

@JsStatic internal const val WASM_INS_F64_PROMOTE_F32 = 187

@JsStatic internal const val WASM_INS_I32_REINTERPRET_F32 = 188

@JsStatic internal const val WASM_INS_I64_REINTERPRET_F64 = 189

@JsStatic internal const val WASM_INS_F32_REINTERPRET_I32 = 190

@JsStatic internal const val WASM_INS_F64_REINTERPRET_I64 = 191

@JsStatic internal const val WASM_INS_INVALID = 512

@JsStatic internal const val WASM_INS_ENDING = 513

@JsStatic internal const val WASM_GRP_INVALID = 0

@JsStatic internal const val WASM_GRP_NUMBERIC = 8

@JsStatic internal const val WASM_GRP_PARAMETRIC = 9

@JsStatic internal const val WASM_GRP_VARIABLE = 10

@JsStatic internal const val WASM_GRP_MEMORY = 11

@JsStatic internal const val WASM_GRP_CONTROL = 12

@JsStatic internal const val WASM_GRP_ENDING = 13

@JsStatic internal const val WASM_OP_INVALID = 0

@JsStatic internal const val WASM_OP_IMM = 2

@JsStatic internal const val WASM_OP_NONE = 16

@JsStatic internal const val WASM_OP_INT7 = 17

@JsStatic internal const val WASM_OP_VARUINT32 = 18

@JsStatic internal const val WASM_OP_VARUINT64 = 19

@JsStatic internal const val WASM_OP_UINT32 = 20

@JsStatic internal const val WASM_OP_UINT64 = 21

@JsStatic internal const val WASM_OP_BRTABLE = 22
