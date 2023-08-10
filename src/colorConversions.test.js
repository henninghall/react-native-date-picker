import { colorToHex } from './colorToHex'

describe('colorToHex', () => {
  it('should not change 6 digit hex string ', () => {
    expect(colorToHex('#ff00ff')).toEqual('#ff00ff')
  })

  it('should extend 3 digit hex string to 6 digit ', () => {
    expect(colorToHex('#f0f')).toEqual('#ff00ff')
  })

  it('should transform rgb string to hex', () => {
    expect(colorToHex('rgb(255,0,255)')).toEqual('#ff00ff')
  })

  it('should transform rgba string to hex and ignore alpha part', () => {
    expect(colorToHex('rgba(255,0,255,0.5)')).toEqual('#ff00ff')
  })

  it('should handle rgba string with spaces within', () => {
    expect(colorToHex('rgba(255,0 , 255 ,0.5)')).toEqual('#ff00ff')
  })

  it('should support color names', () => {
    expect(colorToHex('green')).toEqual('#008000')
  })

  it('should throw when empty color', () => {
    expect(() => colorToHex('')).toThrow(Error)
  })

  it('should throw when invalid color', () => {
    expect(() => colorToHex('#xxx')).toThrow(Error)
  })

  it('should throw when null', () => {
    expect(() => colorToHex(null)).toThrow(Error)
  })

  it('return undefined when undefined', () => {
    expect(colorToHex(undefined)).toEqual(undefined)
  })

  it('should transform hsl to hex', () => {
    expect(colorToHex('hsl(0, 0%, 0%)')).toEqual('#000000')
    expect(colorToHex('hsl(0, 0%, 100%)')).toEqual('#ffffff')
    expect(colorToHex('hsl(0, 0%, 50%)')).toEqual('#808080')
    expect(colorToHex('hsl(34, 87%, 50%)')).toEqual('#ee8e11')
    expect(colorToHex('hsl(137, 83%, 71%)')).toEqual('#78f29a')
    expect(colorToHex('hsl(360 100% 100%)')).toEqual('#ffffff')
    expect(colorToHex('hsla(137, 83%, 71%, 0.5)')).toEqual('#78f29a')
    expect(colorToHex('hsla(360 100% 100% / 1.0)')).toEqual('#ffffff')
  })
})
