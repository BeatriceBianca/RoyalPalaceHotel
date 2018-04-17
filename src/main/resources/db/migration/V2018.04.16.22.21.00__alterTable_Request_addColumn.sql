IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'REQUEST' AND COLUMN_NAME = 'LATE_CHECKOUT')
  BEGIN

    ALTER TABLE [dbo].[Request] ADD
      [LATE_CHECKOUT] BIT DEFAULT 0
  END

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'REQUEST' AND COLUMN_NAME = 'LUNCH')
  BEGIN

    ALTER TABLE [dbo].[Request] ADD
      [LUNCH] BIT DEFAULT 0
  END

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'REQUEST' AND COLUMN_NAME = 'DINNER')
  BEGIN

    ALTER TABLE [dbo].[Request] ADD
      [DINNER] BIT DEFAULT 0
  END