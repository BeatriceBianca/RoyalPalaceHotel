IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'GUEST' AND COLUMN_NAME = 'GUEST_EMAIL')
  BEGIN

    ALTER TABLE [dbo].[Guest]
      ALTER COLUMN
      [Guest_Email] VARCHAR(100)

  END