export function throwIfInvalidProps(props) {
  checks.forEach((check) => check.validate(props))
}

class PropCheck {
  /**
   * @typedef {(props: Props & {style: object}) => boolean | undefined} IsInvalid
   * @param {IsInvalid} isInvalid
   * @param {string} errorText
   */
  constructor(isInvalid, errorText) {
    /** @type {IsInvalid} */
    this.isInvalid = isInvalid
    /** @type {string} */
    this.errorText = errorText
  }

  /**
   * @param {Object} props
   * @throws {Error}
   */
  validate = (props) => {
    if (this.isInvalid(props)) {
      throw new Error(
        `${this.errorText} Check usage of react-native-date-picker.`
      )
    }
  }
}

const dateCheck = new PropCheck(
  (props) => props && !(props.date instanceof Date),
  'Invalid or missing Date prop. Must be a Date object.'
)

const widthCheck = new PropCheck(
  (props) =>
    props &&
    props.style &&
    props.style.width &&
    typeof props.style.width !== 'number',
  'Invalid style: width. Width needs to be a number. Percentages or other values are not supported.'
)

const heightCheck = new PropCheck(
  (props) =>
    props &&
    props.style &&
    props.style.height &&
    typeof props.style.height !== 'number',
  'Invalid style: height. Height needs to be a number. Percentages or other values are not supported.'
)

const modeCheck = new PropCheck(
  (props) =>
    props && props.mode && !['datetime', 'date', 'time'].includes(props.mode),
  "Invalid mode. Valid modes: 'datetime', 'date', 'time'"
)

const themeCheck = new PropCheck(
  (props) =>
    props && props.theme && !['light', 'dark', 'auto'].includes(props.theme),
  "Invalid theme. Valid options: 'light', 'dark', 'auto'"
)

const checks = [dateCheck, widthCheck, heightCheck, modeCheck, themeCheck]
