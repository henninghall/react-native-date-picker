export const readableUtcDate = date =>
  date
    ? date
        .toISOString()
        .substr(0, 19)
        .replace('T', ' ')
    : 'undefined'

export const readableDate = d => {
  if (!d) return undefined
  return `${d.getFullYear()}-${pad2(d.getMonth() + 1)}-${pad2(
    d.getDate()
  )} ${pad2(d.getHours())}:${pad2(d.getMinutes())}:${pad2(d.getSeconds())}`
}

function pad2(string) {
  return `0${string}`.slice(-2)
}
