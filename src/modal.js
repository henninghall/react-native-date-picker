export const shouldOpenModal = (props, prevProps) => {
  if (!props.modal) return false
  if (!props.open) return false
  const currentlyOpen = prevProps?.open
  if (currentlyOpen) return false
  return true
}

export const shouldCloseModal = (props, prevProps, isClosing) => {
  if (!props.modal) return false
  if (props.open) return false
  const currentlyOpen = prevProps?.open
  if (!currentlyOpen) return false
  if (isClosing) return false
  return true
}
