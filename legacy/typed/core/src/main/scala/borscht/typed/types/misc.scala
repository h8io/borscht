package borscht.typed.types

import borscht.typed.types.ValueTypeConstructorOptionalParameter

object ValueTypeConstructorEnv extends ValueTypeConstructorOptionalParameter(sys.env)

object ValueTypeConstructorProp extends ValueTypeConstructorOptionalParameter(sys.props)